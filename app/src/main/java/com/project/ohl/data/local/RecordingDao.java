package com.project.ohl.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecordingDao {
    @Query("SELECT * FROM saved_recordings_table ORDER BY time_added DESC, id DESC")
    LiveData<List<RecordingEntity>> observeAll();

    @Query("SELECT * FROM saved_recordings_table ORDER BY time_added DESC, id DESC")
    List<RecordingEntity> findAll();
}
