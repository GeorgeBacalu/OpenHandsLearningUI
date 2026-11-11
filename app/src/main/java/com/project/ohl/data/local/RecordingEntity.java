package com.project.ohl.data.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.project.ohl.domain.model.RecordingItem;
import java.util.UUID;

@Entity(tableName = "saved_recordings_table")
public class RecordingEntity {
    @NonNull @PrimaryKey public UUID id = UUID.randomUUID();
    public String name;
    public String path;
    public Long length;
    @ColumnInfo(name = "time_added") public Long timeAdded;

    public RecordingItem toDomain() {
        return new RecordingItem(name, path, length == null ? 0 : length, timeAdded == null ? 0 : timeAdded);
    }
}
