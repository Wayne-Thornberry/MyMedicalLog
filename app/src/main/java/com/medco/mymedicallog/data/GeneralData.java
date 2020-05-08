package com.medco.mymedicallog.data;

import java.io.Serializable;

public class GeneralData implements Serializable {
    public String uuid;

    public boolean isDoctorRequestPending;
    public boolean isDoctorConnected;
    public String rejectReason;

    public int doctorId;
    public String doctorName;
    public int doctorPasscode;
    public String doctorStatus;
    public long doctorLastOnline;

    public int patientId;
    public String patientName;
    public String patientGender;
    public int patientAge;
    public String patientPPSNo;
    public String patientDob;

    public boolean isAccountSetup;
    public String accountUsername;
    public String accountEmail;
    public String accountPassword;
}
