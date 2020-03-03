package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.medco.mymedicallog.MyProfile;
import com.medco.mymedicallog.MainActivity;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.database.entities.Profile;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

public class LoadProfileTask extends AsyncTask<Profile, Void, Void> {


    private final WeakReference<Activity> mActivity;

    public LoadProfileTask(Activity activity){
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Void doInBackground(Profile... profiles) {
        Profile profile = profiles[0];
        MyProfile myProfile = MyProfile.getInstance();

        MainDatabase mDb = MainDatabase.getInstance(mActivity.get());
        List<ProfileLog> profileLogs = mDb.logDao().getLogsFromProfile(profile.profileId);
        if(profileLogs.isEmpty()){
            ProfileLog profileLog = new ProfileLog();
            profileLog.profileId = profile.profileId;
            profileLog.dateCreated = new Date();
            profileLog.dateUpdated = new Date();
            mDb.logDao().insert(profileLog);
            profileLogs = mDb.logDao().getLogsFromProfile(profile.profileId);
        }
        List<LogEntry> entries = mDb.entryDao().getEntriesFromLog(profileLogs.get(0).logId);
        myProfile.setProfile(profile);
        myProfile.setLogs(profileLogs);
        myProfile.setEntries(entries);
        myProfile.setActiveLog(0);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
