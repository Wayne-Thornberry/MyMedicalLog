package com.medco.mymedicallog.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.medco.mymedicallog.data.messages.LogEntryMessage;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                onDelete = CASCADE,
                entity = ProfileLog.class,
                parentColumns = "log_id",
                childColumns = "entry_log_id"
        )
})
public class LogEntry implements Serializable {
    // ENTRY INFO
    @ColumnInfo(name = "entry_id")
    @PrimaryKey(autoGenerate = true)
    public long entryId;
    @ColumnInfo(name = "entry_type")
    public String entryType;
    @ColumnInfo(name = "entry_log_id")
    public long entryLogId;
    @ColumnInfo(name = "entry_created_date")
    public long entityCreatedDate;
    @ColumnInfo(name = "entry_parent_id")
    public long entityParentId;
    // DOCTOR INFO
    @ColumnInfo(name = "pain_start_date")
    public long painStartDate;
    @ColumnInfo(name = "pain_duration")
    public String painDuration;
    @ColumnInfo(name = "pain_severity")
    public String painSeverity;
    @ColumnInfo(name = "pain_type")
    public String painType;
    @ColumnInfo(name = "pain_location")
    public String painLocation;
    @ColumnInfo(name = "pain_experience")
    public int painStrength;
    @ColumnInfo(name = "pain_description")
    public String painDescription;
    @ColumnInfo(name = "pain_notice")
    public String painNotice;
    // TREATMENT INFO
    @ColumnInfo(name = "treatment_meds_used")
    public String treatmentMedicationUsed;
    // DOCTOR INFO
    @ColumnInfo(name = "doctor_viewed")
    public boolean doctorViewed;
    @ColumnInfo(name = "doctor_uploaded")
    public boolean doctorUploaded;
    @ColumnInfo(name = "doctor_status")
    public boolean doctorStatus;

    public LogEntryMessage getMessage() {
        LogEntryMessage logEntryMessage = new LogEntryMessage();
        logEntryMessage.entryId = this.entryId;
        logEntryMessage.entryType = this.entryType;
        logEntryMessage.entryLogId = this.entryLogId;
        logEntryMessage.entityCreatedDate = this.entityCreatedDate;
        logEntryMessage.entityParentId = this.entityParentId;
        logEntryMessage.painStartDate = this.painStartDate;
        logEntryMessage.painDuration = this.painDuration;
        logEntryMessage.painSeverity = this.painSeverity;
        logEntryMessage.painType = this.painType;
        logEntryMessage.painLocation = this.painLocation;
        logEntryMessage.painStrength = this.painStrength;
        logEntryMessage.painDescription = this.painDescription;
        logEntryMessage.painNotice = this.painNotice;
        logEntryMessage.treatmentMedicationUsed = this.treatmentMedicationUsed;
        logEntryMessage.doctorViewed = this.doctorViewed;
        logEntryMessage.doctorUploaded = this.doctorUploaded;
        return logEntryMessage;
    }
}
