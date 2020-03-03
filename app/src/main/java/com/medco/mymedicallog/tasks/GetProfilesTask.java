package com.medco.mymedicallog.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.medco.mymedicallog.PortalActivity;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.Profile;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetProfilesTask extends AsyncTask<Void, Void, List<Profile>> {

    private final WeakReference<Activity> mActivity;

    public GetProfilesTask(PortalActivity activity){
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected List<Profile> doInBackground(Void... voids) {
        PortalActivity activity = (PortalActivity) mActivity.get();
        return MainDatabase.getInstance(activity).profileDao().getProfiles();
    }

    @Override
    protected void onPostExecute(List<Profile> profiles) {
        super.onPostExecute(profiles);
        PortalActivity activity = (PortalActivity) mActivity.get();
        activity.startProfileListFragment(profiles);
    }
}
