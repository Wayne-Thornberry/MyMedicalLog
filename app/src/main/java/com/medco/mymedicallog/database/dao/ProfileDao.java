package com.medco.mymedicallog.database.dao;

import androidx.room.*;
import com.medco.mymedicallog.database.entities.Profile;
import com.medco.mymedicallog.database.relationships.ProfileWithLogs;

import java.util.List;

@Dao
public interface ProfileDao {
    @Transaction
    @Query("SELECT * FROM Profile LIMIT 5")
    List<Profile> getProfiles();

    @Insert
    void insert(Profile profile);

    @Delete
    void delete(Profile profile);

    @Transaction
    @Query("SELECT * FROM Profile WHERE profileId = :id LIMIT 5")
    List<ProfileWithLogs> getProfilesWithLogs(long id);

    // Noodles with veg
    // Diced chicken curry
}
