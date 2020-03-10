package com.medco.mymedicallog.data.messages;

import com.medco.mymedicallog.data.UserProfile;

import java.io.Serializable;

public class DoctorRequestMessage implements Serializable {
    public int doctorID;
    public int passcode;
    public boolean scannedQR;
    public UserProfile userProfile;
}
