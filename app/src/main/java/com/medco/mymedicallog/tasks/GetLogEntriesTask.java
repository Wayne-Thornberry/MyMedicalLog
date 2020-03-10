package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import com.medco.mymedicallog.MyProfile;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetLogEntriesTask extends AsyncTask<Long, Void, List<LogEntry>> {

    private final OnTaskCompleteListener mListener;
    private final WeakReference<Activity> mActivity;

    public GetLogEntriesTask(Activity activity, OnTaskCompleteListener mTaskCompleteListener){
        mListener = mTaskCompleteListener;
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected List<LogEntry> doInBackground(Long... longs) {
        Log.e("Retriving Entries", "Retriving");
        Activity activity = mActivity.get();
        return MainDatabase.getInstance(activity).entryDao().getEntriesFromLog(longs[0]);
    }

    @Override
    protected void onPostExecute(List<LogEntry> logEntries) {
        Log.e("Retriving Entries", "Done");
        MyProfile myProfile = MyProfile.getInstance();
        myProfile.setEntries(logEntries);
        mListener.onTaskComplete(0);
    }
}
