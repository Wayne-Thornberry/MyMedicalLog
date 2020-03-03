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
    @Query("SELECT * FROM LogEntry WHERE logId = :logId")
    List<LogEntry> getEntriesFromLog(long logId);
}
