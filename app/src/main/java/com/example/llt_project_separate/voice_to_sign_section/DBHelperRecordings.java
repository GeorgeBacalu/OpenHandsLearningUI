package com.example.llt_project_separate.voice_to_sign_section;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelperRecordings extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "saved_recordings.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "saved_recordings_table";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_LENGTH = "length";
    public static final String COLUMN_TIME_ADDED = "time_added";

    private static OnDatabaseChangedListener OnDatabaseChangedListener;

    public static final String COMMA_SEPARATOR = ",";

    private static final String SQLITE_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY " +
            "AUTOINCREMENT" + COMMA_SEPARATOR +
            COLUMN_NAME + " TEXT" + COMMA_SEPARATOR +
            COLUMN_PATH + " TEXT" + COMMA_SEPARATOR +
            COLUMN_LENGTH + " INTEGER" + COMMA_SEPARATOR +
            COLUMN_TIME_ADDED + " INTEGER )";

    public DBHelperRecordings(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLITE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addRecording(RecordingItem recordingItem) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, recordingItem.getName());
            contentValues.put(COLUMN_PATH, recordingItem.getPath());
            contentValues.put(COLUMN_LENGTH, recordingItem.getLength());
            contentValues.put(COLUMN_TIME_ADDED, recordingItem.getTimeAdded());
            db.insert(TABLE_NAME, null, contentValues);
            if(OnDatabaseChangedListener != null) {
                OnDatabaseChangedListener.onNewDatabaseEntryAdded(recordingItem);
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<RecordingItem> getAllAudios() {
        ArrayList<RecordingItem> recordingItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                String name = cursor.getString(1);
                String path = cursor.getString(1);
                int length = (int)cursor.getLong(3);
                long timeAdded = cursor.getLong(4);
                RecordingItem recordingItem = new RecordingItem(name, path, length, timeAdded);
                recordingItems.add(recordingItem);
            }
            cursor.close();
            return recordingItems;
        }
        return null;
    }

    public static void setOnDatabaseChangedListener(OnDatabaseChangedListener listener) {
        OnDatabaseChangedListener = listener;
    }
}
