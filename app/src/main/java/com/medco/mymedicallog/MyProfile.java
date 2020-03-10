package com.medco.mymedicallog;

import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;

import java.util.List;

public class MyProfile {

    private static MyProfile mInstance;

    private List<ProfileLog> mProfileLogs;
    private List<LogEntry> mEntries;

    private ProfileLog mSelectedProfileLog;
    private int mActiveLog;

    public static MyProfile getInstance(){
        if(mInstance == null)
            mInstance = new MyProfile();
        return mInstance;
    }

    public void setLogs(List<ProfileLog> profileLogs){
        mProfileLogs = profileLogs;
    }

    public ProfileLog setActiveLog(int id){
        if(id < mProfileLogs.size()) {
            mActiveLog = id;
            mSelectedProfileLog = mProfileLogs.get(id);
        }
        return mSelectedProfileLog;
    }

    public List<ProfileLog> getLogs(){
        return mProfileLogs;
    }


    @Override
    public String toString() {
        return "MyProfile{" +
                ", mLogs=" + mProfileLogs +
                ", mEntries=" + mEntries +
                ", mSelectedLog=" + mSelectedProfileLog +
                ", mActiveLog=" + mActiveLog +
                '}';
    }

    public void setEntries(List<LogEntry> entries) {
        mEntries = entries;
    }

    public List<LogEntry> getEntries() {
        return mEntries;
    }

    public ProfileLog getActiveLog() {
        if(mSelectedProfileLog == null) setActiveLog(0);
        return mSelectedProfileLog;
    }
}
