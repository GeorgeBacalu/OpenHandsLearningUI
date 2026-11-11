package com.project.ohl;

import android.content.Context;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.data.local.RecordingDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RecordingMigrationTest {
    @Test public void preservesLegacyAudioPathsAndMetadata() {
        verifyMigration(1);
    }

    @Test public void preservesRoomAudioPathsAndMetadata() {
        verifyMigration(2);
    }

    private void verifyMigration(int version) {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String name = "recording-migration-test.db";
        context.deleteDatabase(name);
        try {
            try (var legacy = context.openOrCreateDatabase(name, Context.MODE_PRIVATE, null)) {
                legacy.execSQL("CREATE TABLE saved_recordings_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                        + "name TEXT, path TEXT, length INTEGER, time_added INTEGER)");
                legacy.execSQL("INSERT INTO saved_recordings_table(name,path,length,time_added) VALUES (?,?,?,?)",
                        new Object[]{"My recording", "/recordings/audio.m4a", 1234L, 5678L});
                legacy.execSQL("INSERT INTO saved_recordings_table(name,path,length,time_added) VALUES (?,?,?,?)",
                        new Object[]{"Second recording", "/recordings/second.m4a", null, null});
                legacy.setVersion(version);
            }
            var database = Room.databaseBuilder(context, RecordingDatabase.class, name)
                    .addMigrations(RecordingDatabase.FROM_LEGACY, RecordingDatabase.TO_UUID).build();
            try {
                var items = database.recordings().findAll();
                assertEquals(2, items.size());
                assertNotNull(items.get(0).id);
                assertNotNull(items.get(1).id);
                assertEquals(4, items.get(0).id.version());
                assertNotEquals(items.get(0).id, items.get(1).id);
                var item = items.get(0).toDomain();
                assertEquals("My recording", item.getName());
                assertEquals("/recordings/audio.m4a", item.getPath());
                assertEquals(1234L, item.getLength());
                assertEquals(5678L, item.getTimeAdded());
                assertEquals("/recordings/second.m4a", items.get(1).path);
                assertNull(items.get(1).length);
                assertNull(items.get(1).timeAdded);
            } finally { database.close(); }
        } finally { context.deleteDatabase(name); }
    }
}
