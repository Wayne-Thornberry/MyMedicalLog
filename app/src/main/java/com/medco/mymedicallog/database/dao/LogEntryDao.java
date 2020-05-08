package com.medco.mymedicallog.database.dao;

import androidx.room.*;
import com.medco.mymedicallog.database.entities.LogEntry;

import java.util.List;

@Dao
public interface LogEntryDao {
    @Insert
    void insert(LogEntry... logEntry);

    @Delete
    void delete(LogEntry[] logEntry);

    @Delete
    void delete(LogEntry logEntry);

    @Transaction
    @Query("SELECT * FROM LogEntry")
    List<LogEntry> select();

    @Transaction
    @Query("SELECT * FROM LogEntry WHERE entry_log_id = :id ")
    List<LogEntry> select(long id);


    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(List<LogEntry> entries);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(LogEntry entry);
}
