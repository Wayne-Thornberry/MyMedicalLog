package com.medco.mymedicallog.database.dao;

import androidx.room.*;
import com.medco.mymedicallog.database.entities.ProfileLog;

import java.util.List;

@Dao
public interface ProfileLogDao {

    @Insert
    void insert(ProfileLog profileLog);

    @Delete
    void delete(ProfileLog profileLog);

    @Transaction
    @Query("SELECT * FROM ProfileLog WHERE profileId = :profileId")
    List<ProfileLog> getLogsFromProfile(long profileId);
}
