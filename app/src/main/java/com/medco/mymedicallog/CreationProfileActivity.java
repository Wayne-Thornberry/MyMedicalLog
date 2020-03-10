package com.medco.mymedicallog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.medco.mymedicallog.data.UserProfile;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

public class CreationProfileActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void submitDetails(View view) {
        Intent intent = new Intent();
        UserProfile userProfile = new UserProfile();
        userProfile.firstName = "";
        userProfile.lastName = "";
        userProfile.isProfileSetup = true;
        intent.putExtra("PROFILE", userProfile);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
