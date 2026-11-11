package com.project.ohl.data.local;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.UUID;

@Database(entities = RecordingEntity.class, version = 3)
@TypeConverters(RecordingDatabase.UuidConverter.class)
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

    public static final Migration TO_UUID = new Migration(2, 3) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE saved_recordings_new (id TEXT NOT NULL PRIMARY KEY, "
                    + "name TEXT, path TEXT, length INTEGER, time_added INTEGER)");
            try (var rows = database.query("SELECT name, path, length, time_added FROM saved_recordings_table ORDER BY id")) {
                while (rows.moveToNext()) {
                    database.execSQL("INSERT INTO saved_recordings_new (id, name, path, length, time_added) VALUES (?, ?, ?, ?, ?)",
                            new Object[]{UUID.randomUUID().toString(), rows.getString(0), rows.getString(1),
                                    rows.isNull(2) ? null : rows.getLong(2), rows.isNull(3) ? null : rows.getLong(3)});
                }
            }
            database.execSQL("DROP TABLE saved_recordings_table");
            database.execSQL("ALTER TABLE saved_recordings_new RENAME TO saved_recordings_table");
        }
    };

    public static class UuidConverter {
        @TypeConverter public static String encode(UUID id) {
            return id == null ? null : id.toString();
        }

        @TypeConverter public static UUID decode(String id) {
            return id == null ? null : UUID.fromString(id);
        }
    }

    private static volatile RecordingDatabase instance;

    public static RecordingDatabase get(Context context) {
        if (instance == null) {
            synchronized (RecordingDatabase.class) {
                if (instance == null) instance = Room.databaseBuilder(context.getApplicationContext(),
                        RecordingDatabase.class, "saved_recordings.db").addMigrations(FROM_LEGACY, TO_UUID).build();
            }
        }
        return instance;
    }
}
