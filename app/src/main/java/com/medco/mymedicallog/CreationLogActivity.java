package com.medco.mymedicallog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

import java.util.Date;

public class CreationLogActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_log);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void submitDetails(View view) {
        Intent intent = new Intent();
        ProfileLog profileLog = new ProfileLog();
        profileLog.dateCreated = new Date();
        profileLog.dateUpdated = new Date();
        intent.putExtra("PROFILELOG", profileLog);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
