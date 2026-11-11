package com.project.ohl.data.local;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = RecordingEntity.class, version = 2)
public abstract class RecordingDatabase extends RoomDatabase {
    public abstract RecordingDao recordings();

    public static final Migration FROM_LEGACY = new Migration(1, 2) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE saved_recordings_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "name TEXT, path TEXT, length INTEGER, time_added INTEGER)");
            database.execSQL("INSERT INTO saved_recordings_new (id, name, path, length, time_added) "
                    + "SELECT id, name, path, length, time_added FROM saved_recordings_table");
            database.execSQL("DROP TABLE saved_recordings_table");
            database.execSQL("ALTER TABLE saved_recordings_new RENAME TO saved_recordings_table");
        }
    };

    private static volatile RecordingDatabase instance;

    public static RecordingDatabase get(Context context) {
        if (instance == null) {
            synchronized (RecordingDatabase.class) {
                if (instance == null) instance = Room.databaseBuilder(context.getApplicationContext(),
                        RecordingDatabase.class, "saved_recordings.db").addMigrations(FROM_LEGACY).build();
            }
        }
        return instance;
    }
}
