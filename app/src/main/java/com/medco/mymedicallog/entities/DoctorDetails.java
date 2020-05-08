package com.medco.mymedicallog.entities;

import java.io.Serializable;

public class DoctorDetails implements Serializable {
    public String name;
    public int id;
    public String queue;
    public boolean isConnected;
    public boolean isRequestPending;
    public String status;
    public long lastOnline;
}
