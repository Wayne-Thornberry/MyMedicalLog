package com.medco.mymedicallog.database.dao;

import androidx.room.*;
import com.medco.mymedicallog.database.entities.LogEntry;

import java.util.List;

@Dao
public interface LogEntryDao {
    @Insert
    void insert(LogEntry... logEntry);

    @Delete
    void delete(LogEntry logEntry);

    @Transaction
    @Query("SELECT * FROM LogEntry")
    List<LogEntry> getEntries();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(LogEntry[] entries);
}
