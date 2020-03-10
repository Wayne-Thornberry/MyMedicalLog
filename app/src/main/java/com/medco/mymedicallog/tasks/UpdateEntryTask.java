package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;

import java.lang.ref.WeakReference;

public class UpdateEntryTask extends AsyncTask<LogEntry,Void,Void> {

    private final WeakReference<Context> mActivity;
    private final int mRequestCode;

    public UpdateEntryTask(Context activity, int requestCode){
        mRequestCode = requestCode;
        mActivity = new WeakReference<Context>(activity);
    }

    @Override
    protected Void doInBackground(LogEntry... entries) {
        MainDatabase.getInstance(mActivity.get()).entryDao().update(entries);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        OnTaskCompleteListener onTaskCompleteListener = (OnTaskCompleteListener) mActivity.get();
        onTaskCompleteListener.onTaskComplete(mRequestCode);
    }
}
