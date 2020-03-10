package com.medco.mymedicallog;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

public class EntryViewActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private BottomNavigationView mNavigationView;
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);
        Intent intent = getIntent();
        LogEntry entry = (LogEntry) intent.getSerializableExtra("LOGENTRY");
        if(entry != null)
            Log.e("ENTRY", entry.painType);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNavigationView = findViewById(R.id.bottom_navigation);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragments);
        NavigationUI.setupWithNavController(mNavigationView, mNavController);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
