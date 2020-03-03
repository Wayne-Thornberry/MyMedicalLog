package com.medco.mymedicallog;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.medco.mymedicallog.database.entities.Profile;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnPortalListFragmentInteractionListener;
import com.medco.mymedicallog.tasks.LoadProfileTask;
import com.medco.mymedicallog.tasks.GetProfilesTask;
import com.medco.mymedicallog.ui.portal.selection.PortalSelectionFragment;

import java.util.List;

public class PortalActivity extends AppCompatActivity implements OnPortalListFragmentInteractionListener, OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        new GetProfilesTask(this).execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        //Bundle extras = intent.getExtras();
        //Log.e("It worked", "It called back the result 2");
        setResult(RESULT_OK,intent);
        finish();
    }

    public void startProfileListFragment(List<Profile> profiles){
        PortalSelectionFragment selectionFragment = PortalSelectionFragment.newInstance(profiles);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectionFragment)
                .commitNow();
    }

    @Override
    public void onListFragmentInteractionListener(Profile profile) {
        Log.e(profile.firstName, profile.lastName);
        new LoadProfileTask(this).execute(profile);
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }


    @Override
    public void onFragmentInteractionListener(Uri uri) {
    }

    public void startCreationActivity(View view) {
        Intent intent = new Intent(this, CreationProfileActivity.class);
        startActivityForResult(intent, 222);
    }
}
