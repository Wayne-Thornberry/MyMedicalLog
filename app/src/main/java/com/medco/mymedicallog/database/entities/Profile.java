package com.medco.mymedicallog.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Profile implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long profileId;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    @ColumnInfo(name = "ppsno")
    public String ppsno;
    @ColumnInfo(name = "dob")
    public Date dob;
    @ColumnInfo(name = "age")
    public String age;
    @ColumnInfo(name = "date_created")
    public Date dateCreated;
    @ColumnInfo(name = "date_updated")
    public Date dateUpdated;
    @ColumnInfo(name = "phone_no")
    public String phoneNo;
    @ColumnInfo(name = "email_address")
    public String emailAddress;
    @ColumnInfo(name = "home_address_line_one")
    public String homeAddressLineOne;
    @ColumnInfo(name = "home_address_line_two")
    public String homeAddressLineTwo;
    @ColumnInfo(name = "home_address_line_three")
    public String homeAddressLineThree;
    @ColumnInfo(name = "blood_type")
    public String bloodType;
}
