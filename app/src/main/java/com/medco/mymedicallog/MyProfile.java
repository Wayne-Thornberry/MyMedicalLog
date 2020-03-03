package com.medco.mymedicallog;

import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.database.entities.Profile;

import java.util.List;

public class MyProfile {

    private static MyProfile mInstance;

    private Profile mProfile;
    private List<ProfileLog> mProfileLogs;
    private List<LogEntry> mEntries;

    private ProfileLog mSelectedProfileLog;
    private int mActiveLog;

    public static MyProfile getInstance(){
        if(mInstance == null)
            mInstance = new MyProfile();
        return mInstance;
    }

    public void setProfile(Profile profile) {
        mProfile = profile;
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

    public Profile getProfile(){
        return mProfile;
    }

    @Override
    public String toString() {
        return "MyProfile{" +
                "mProfile=" + mProfile +
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
        return mSelectedProfileLog;
    }
}
