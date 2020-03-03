package com.medco.mymedicallog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnDisplayListFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;
import com.medco.mymedicallog.tasks.InsertEntriesTask;
import com.medco.mymedicallog.tasks.LoadProfileTask;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnDisplayListFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    private NavigationView mNavigationView;
    private FloatingActionButton mFab;
    private DrawerLayout mDrawer;

    public static void newInstance(Activity context) {
        Intent myIntent = new Intent(context, MainActivity.class);
        context.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(true)
            startSelectProfileActivity();
        else
            startThisActivity();
    }

    private void startThisActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = findViewById(R.id.fab);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile,
                R.id.nav_summery,
                R.id.nav_log,
                R.id.nav_avatar)
                .setDrawerLayout(mDrawer)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, mNavController);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        new LoadProfileTask(this).execute(MyProfile.getInstance().getProfile());
        Log.e("It worked", "It called back the result");
    }

    private void startSelectProfileActivity() {
        Intent indent = new Intent(this,PortalActivity.class);
        startActivityForResult(indent, 233);
        startThisActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_options, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteractionListener(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(LogEntry item) {

    }

    public void doSomething(View view) {
        Log.e("It worked", "It called back the result");
    }

    public void insertEntry(View view) {
        Intent intent = new Intent(this, EntryCreationActivity.class);
        startActivityForResult(intent, 233);
    }

    public void switchProfiles(MenuItem item) {
        startSelectProfileActivity();
    }
}
