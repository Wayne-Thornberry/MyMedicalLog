package com.medco.mymedicallog.database.dao;

import androidx.room.*;
import com.medco.mymedicallog.database.entities.ProfileLog;

import java.util.List;

@Dao
public interface ProfileLogDao {

    @Insert
    void insert(ProfileLog profileLog);

    @Delete
    void delete(ProfileLog... profileLog);

    @Delete
    void delete(ProfileLog profileLog);

    @Transaction
    @Query("SELECT * FROM ProfileLog")
    List<ProfileLog> select();

    @Transaction
    @Query("SELECT * FROM ProfileLog WHERE log_name LIKE :logName")
    ProfileLog select(String logName);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(List<ProfileLog> logs);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(ProfileLog log);
}
