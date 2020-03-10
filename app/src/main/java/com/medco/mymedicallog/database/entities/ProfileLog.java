package com.medco.mymedicallog.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class ProfileLog implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long logId;
    @ColumnInfo(name = "date_created")
    public Date dateCreated;
    @ColumnInfo(name = "date_updated")
    public Date dateUpdated;
}
