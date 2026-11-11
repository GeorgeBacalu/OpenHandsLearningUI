package com.project.ohl.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.project.ohl.domain.model.RecordingItem;

@Entity(tableName = "saved_recordings_table")
public class RecordingEntity {
    @PrimaryKey(autoGenerate = true) public long id;
    public String name;
    public String path;
    public Long length;
    @ColumnInfo(name = "time_added") public Long timeAdded;

    public RecordingItem toDomain() {
        return new RecordingItem(name, path, length == null ? 0 : length, timeAdded == null ? 0 : timeAdded);
    }
}
