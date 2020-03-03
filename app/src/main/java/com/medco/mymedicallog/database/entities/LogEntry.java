package com.medco.mymedicallog.database.entities;

import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class LogEntry {
    @PrimaryKey(autoGenerate = true)
    public long entryId;
    @ColumnInfo(name = "logId")
    public long logId;
    @ColumnInfo(name = "recorded_date")
    public Date recordedDate;
    @ColumnInfo(name = "est_start_date")
    public Date estStartDate;
    @ColumnInfo(name = "pain_duration")
    public String painDuration;
    @ColumnInfo(name = "pain_severity")
    public String painSeverity;
    @ColumnInfo(name = "pain_type")
    public String painType;
    @ColumnInfo(name = "pain_location")
    public String painLocation;
    @ColumnInfo(name = "pain_experiance")
    public String painExperience;
    @ColumnInfo(name = "full_description")
    public String fullDescription;
    @ColumnInfo(name = "medication_used")
    public String medicationUsed;
}
