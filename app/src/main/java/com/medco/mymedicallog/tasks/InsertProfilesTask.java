package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.medco.mymedicallog.PortalActivity;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.Profile;

import java.lang.ref.WeakReference;

public class InsertProfilesTask extends AsyncTask<Profile,Void,Void> {

    private final WeakReference<Activity> mActivity;

    public InsertProfilesTask(Activity activity){
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Void doInBackground(Profile... profiles) {
        PortalActivity activity = (PortalActivity) mActivity.get();
        MainDatabase.getInstance(activity).profileDao().insert(profiles[0]);
        return null;
    }
}
