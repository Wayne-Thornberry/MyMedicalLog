package com.medco.mymedicallog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnLogEntryListFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

import java.util.Date;

public class CreationEntryActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnLogEntryListFragmentInteractionListener {

    private int mViewDisplayed;
    private NavController mNavController;
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationView = findViewById(R.id.bottom_navigation);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragments);
        NavigationUI.setupWithNavController(mNavigationView, mNavController);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteractionListener(LogEntry item) {

    }

    public void finishEntry(View view) {
        LogEntry logEntry = new LogEntry();
        logEntry.logId = MyProfile.getInstance().getActiveLog().logId;
        logEntry.estStartDate = new Date();
        logEntry.fullDescription = "Test Description";
        logEntry.medicationUsed = "Parc";
        logEntry.painDuration = "2 Days";
        logEntry.painLocation = "HEAD_TEMPLE";
        logEntry.painExperience = "Debilitating";
        logEntry.painType = "Sharp";
        Intent intent = new Intent();
        intent.putExtra("LOGENTRY", logEntry);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
