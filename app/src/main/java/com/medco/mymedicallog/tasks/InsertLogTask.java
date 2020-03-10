package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;

import java.lang.ref.WeakReference;

public class InsertLogTask extends AsyncTask<ProfileLog,Void,Void> {

    private final WeakReference<Activity> mActivity;
    private final int mRequestCode;

    public InsertLogTask(Activity activity, int requestCode){
        mRequestCode = requestCode;
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Void doInBackground(ProfileLog... profilesLogs) {
        Activity activity = (Activity) mActivity.get();
        MainDatabase.getInstance(activity).logDao().insert(profilesLogs[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        OnTaskCompleteListener onTaskCompleteListener = (OnTaskCompleteListener) mActivity.get();
        onTaskCompleteListener.onTaskComplete(mRequestCode);
    }
}
