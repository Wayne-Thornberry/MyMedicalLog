package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.LogEntry;

import java.lang.ref.WeakReference;

public class InsertEntriesTask extends AsyncTask<LogEntry,Void,Void> {

    private final WeakReference<Activity> mActivity;

    public InsertEntriesTask(Activity activity){
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Void doInBackground(LogEntry... entries) {
        MainDatabase.getInstance(mActivity.get()).entryDao().insert(entries);
        return null;
    }
}
