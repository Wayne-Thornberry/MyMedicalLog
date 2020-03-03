package com.medco.mymedicallog;

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
import com.medco.mymedicallog.interfaces.OnDisplayListFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;
import com.medco.mymedicallog.tasks.InsertEntriesTask;
import com.medco.mymedicallog.ui.main.entry.EntryViewFragment;

import java.util.Date;

public class CreationEntryActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnDisplayListFragmentInteractionListener {

    private int mViewDisplayed;
    private NavController mNavController;
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNavigationView = findViewById(R.id.bottom_navigation);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragments);
        NavigationUI.setupWithNavController(mNavigationView, mNavController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_creation_options, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentInteractionListener(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(LogEntry item) {
        EntryViewFragment entryViewFragment = EntryViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, entryViewFragment)
                .commitNow();
        mViewDisplayed = 3;
    }

    public void insertEntry(View view) {
        LogEntry logEntry = new LogEntry();
        logEntry.logId = MyProfile.getInstance().getActiveLog().logId;
        logEntry.estStartDate = new Date();
        logEntry.fullDescription = "Test Description";
        logEntry.medicationUsed = "Parc";
        logEntry.painDuration = "2 Days";
        logEntry.painLocation = "HEAD_TEMPLE";
        logEntry.painExperience = "Debilitating";
        logEntry.painType = "Sharp";
        new InsertEntriesTask(this).execute(logEntry);
        finish();
    }
}
