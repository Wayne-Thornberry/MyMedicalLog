package com.medco.mymedicallog.entities;

import java.io.Serializable;
import java.util.Date;

public class PatientDetails implements Serializable {
    public int id;
    public String name;
    public int age;
    public String dob;
    public String queue;
    public String ppsno;
}
