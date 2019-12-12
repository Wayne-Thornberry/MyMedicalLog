package com.mymedicallog.core.objects;

import java.util.Date;

public class MedicalEntry {
    Date recordedDate;
    Date estStartDate;
    String painDuration;
    int painSeverity;
    String painType;
    String painLocation;
    String painExperience;
    String fullDescription;
    String[] medicationUsed;

    public MedicalEntry(Date recordedDate, Date estStartDate, String painDuration, int painSeverity, String painType, String painLocation, String painExperience, String fullDescription, String[] medicationUsed) {
        this.recordedDate = recordedDate;
        this.estStartDate = estStartDate;
        this.painDuration = painDuration;
        this.painSeverity = painSeverity;
        this.painType = painType;
        this.painLocation = painLocation;
        this.painExperience = painExperience;
        this.fullDescription = fullDescription;
        this.medicationUsed = medicationUsed;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public Date getEstStartDate() {
        return estStartDate;
    }

    public String getPainDuration() {
        return painDuration;
    }

    public int getPainSeverity() {
        return painSeverity;
    }

    public String getPainType() {
        return painType;
    }

    public String getPainLocation() {
        return painLocation;
    }

    public String getPainExperience() {
        return painExperience;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String[] getMedicationUsed() {
        return medicationUsed;
    }
}
