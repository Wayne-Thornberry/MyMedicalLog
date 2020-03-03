package com.medco.mymedicallog.database.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;

import java.util.List;

public class LogWithEntries {
    @Embedded
    public ProfileLog profileLog;
    @Relation(
            parentColumn = "logId",
            entityColumn = "entryId"
    )
    public List<LogEntry> entries;
}
