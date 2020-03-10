package com.medco.mymedicallog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.medco.mymedicallog.data.messages.DoctorRequestMessage;
import com.medco.mymedicallog.data.UserProfile;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

public class CreationDoctorActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private UserProfile mUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_doctor);
        mUserProfile = (UserProfile) getIntent().getSerializableExtra("PROFILE");
        Log.e(mUserProfile.firstName, mUserProfile.lastName);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void submitDetails(View view) {
        Intent intent = new Intent();
        DoctorRequestMessage profile = new DoctorRequestMessage();
        profile.doctorID = 25565;
        profile.passcode = 2354;
        profile.userProfile = mUserProfile;
        profile.scannedQR = true;
        intent.putExtra("DOCTORREQUEST", profile);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
