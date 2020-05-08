package com.medco.mymedicallog.data.messages;

import java.util.Date;

public class LogEntryMessage {
    public long entryId;
    public String entryType;
    public long entryLogId;
    public long entityCreatedDate;
    public long entityParentId;
    public long painStartDate;
    public String painDuration;
    public String painSeverity;
    public String painType;
    public String painLocation;
    public int painStrength;
    public String painDescription;
    public String painNotice;
    public String treatmentMedicationUsed;
    public boolean doctorViewed;
    public boolean doctorUploaded;
    public boolean doctorStatus;
}
