package com.medco.mymedicallog.database.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.database.entities.Profile;

import java.util.List;

public class ProfileWithLogs {
    @Embedded
    public Profile profile;
    @Relation(
            parentColumn = "profileId",
            entityColumn = "logId"
    )
    public List<ProfileLog> profileLogs;
}
