package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import com.medco.mymedicallog.MyProfile;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetLogsTask extends AsyncTask<Void, Void, List<ProfileLog>> {

    private final OnTaskCompleteListener mListener;
    private final WeakReference<Activity> mActivity;

    public GetLogsTask(Activity activity,OnTaskCompleteListener mTaskCompleteListener){
        mListener = mTaskCompleteListener;
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected List<ProfileLog> doInBackground(Void... voids) {
        Log.e("LOGSTASK", "Done");
        Activity activity = mActivity.get();
        return MainDatabase.getInstance(activity).logDao().getLogs();
    }

    @Override
    protected void onPostExecute(List<ProfileLog> profileLogs) {
        Log.e("LOGSTASK", profileLogs.size() + "");
        MyProfile myProfile = MyProfile.getInstance();
        myProfile.setLogs(profileLogs);
        mListener.onTaskComplete(0);
    }
}
