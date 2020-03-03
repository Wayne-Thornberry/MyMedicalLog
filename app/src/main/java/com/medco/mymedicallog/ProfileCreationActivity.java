package com.medco.mymedicallog;

import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

public class ProfileCreationActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.action_next, R.id.action_prev,
                R.id.nav_log, R.id.nav_avatar)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(toolbar, mNavController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creation_options, menu);
        return true;
    }

    @Override
    public void onFragmentInteractionListener(Uri uri) {

    }

    /*

        Profile test = new Profile();
        test.firstName = "Test";
        test.lastName = "Testing";
        test.age = "23";
        test.bloodType = "B+";
        test.dateCreated = new Date();
        test.dateUpdated = new Date();
        test.emailAddress = "B00000000@mytudublin.ie";
        test.phoneNo = "0859873432";
        test.dob = new Date(1991,12,3);
        test.ppsno = "9348587O";
        new InsertProfilesTask(this).execute(test);
     */
}
