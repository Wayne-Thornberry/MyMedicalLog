package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.medco.mymedicallog.MyProfile;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoadEntriesFromLog extends AsyncTask<ProfileLog, Void, Void> {


    private final WeakReference<Activity> mActivity;

    public LoadEntriesFromLog(Activity activity){
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Void doInBackground(ProfileLog... profileLogs) {
        ProfileLog profileLog = profileLogs[0];
        if(profileLog == null) return null;
        MyProfile myProfile = MyProfile.getInstance();
        MainDatabase mainDatabase = MainDatabase.getInstance(mActivity.get());
        List<LogEntry> entries = mainDatabase.entryDao().getEntriesFromLog(profileLog.logId);
        myProfile.setEntries(entries);
        return null;
    }
}
