package com.medco.mymedicallog.data;

import java.io.Serializable;

public class DoctorRequest implements Serializable {
    public int doctorID;
    public int passcode;
    public boolean scannedQR;
    public UserProfile userProfile;
}
