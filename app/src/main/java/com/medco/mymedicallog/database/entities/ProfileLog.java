package com.medco.mymedicallog.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.medco.mymedicallog.data.messages.LogEntryMessage;
import com.medco.mymedicallog.data.messages.ProfileLogMessage;

import java.io.Serializable;
import java.util.Date;

@Entity
public class ProfileLog implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id")
    public long logId;
    @ColumnInfo(name = "log_name")
    public String logName;
    @ColumnInfo(name = "log_created_date")
    public long logCreatedDate;
    @ColumnInfo(name = "log_updated_date")
    public long logUpdatedDate;
    @ColumnInfo(name = "log_enable_backup")
    public boolean logEnableAutoBackup;
    @ColumnInfo(name = "log_enable_auto_upload")
    public boolean logEnableAutoUpload;


    public ProfileLogMessage getMessage() {
        ProfileLogMessage logEntryMessage = new ProfileLogMessage();
        logEntryMessage.logId = this.logId;
        logEntryMessage.logName = this.logName;
        logEntryMessage.logCreatedDate = this.logCreatedDate;
        logEntryMessage.logUpdatedDate = this.logUpdatedDate;
        logEntryMessage.logEnableAutoBackup = this.logEnableAutoBackup;
        logEntryMessage.logEnableAutoUpload = this.logEnableAutoUpload;
        return logEntryMessage;
    }
}
