package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;

import java.lang.ref.WeakReference;

public class InsertEntriesTask extends AsyncTask<LogEntry,Void,Void> {

    private final WeakReference<Activity> mActivity;
    private final int mRequestCode;

    public InsertEntriesTask(Activity activity, int requestCode){
        mRequestCode = requestCode;
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Void doInBackground(LogEntry... entries) {
        MainDatabase.getInstance(mActivity.get()).entryDao().insert(entries);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        OnTaskCompleteListener onTaskCompleteListener = (OnTaskCompleteListener) mActivity.get();
        onTaskCompleteListener.onTaskComplete(mRequestCode);
    }
}
