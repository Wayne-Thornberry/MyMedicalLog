package com.medco.mymedicallog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medco.mymedicallog.data.DoctorProfile;
import com.medco.mymedicallog.data.messages.DoctorRequestMessage;
import com.medco.mymedicallog.data.UserProfile;
import com.medco.mymedicallog.data.messages.EntryViewedMessage;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.interfaces.OnLogEntryListFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnProfileLogListFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;
import com.medco.mymedicallog.services.RabbitMQService;
import com.medco.mymedicallog.tasks.*;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnProfileLogListFragmentInteractionListener, OnLogEntryListFragmentInteractionListener, OnTaskCompleteListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    private NavigationView mNavigationView;
    private FloatingActionButton mFab;
    private DrawerLayout mDrawer;
    private View mHolder;
    private Gson mGson;

    public static UserProfile mUserProfile;
    public static DoctorProfile mDoctorProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetLogsTask(this, this).execute();
        new GetLogEntriesTask(this, this).execute();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGson = new GsonBuilder().setPrettyPrinting().create();
        mFab = findViewById(R.id.fab);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDoctorProfile = new DoctorProfile();
        mDoctorProfile.Id = 25565;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile,
                R.id.nav_summery,
                R.id.nav_entries,
                R.id.nav_avatar)
                .setDrawerLayout(mDrawer)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, mNavController);
        loadProfile();
        refreshMenu();
    }

    private void saveProfile(UserProfile userProfile){
        String json = mGson.toJson(userProfile);
        try (FileOutputStream fos = this.openFileOutput("Profile", Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void deleteProfile(){
        File fdelete = new File(getFilesDir().getAbsolutePath(), "Profile");
        if (fdelete.exists()) {
            if (fdelete.delete()) {

            } else {

            }
        }
    }

    private void loadProfile() {
        try{
            FileInputStream fis = this.openFileInput("Profile");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            String contents = stringBuilder.toString();
            mUserProfile = mGson.fromJson(contents, UserProfile.class);
        }catch (Exception e){
            e.printStackTrace();
            mUserProfile = new UserProfile();
            saveProfile(mUserProfile);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == -1){
            if(requestCode == 222){ // inserting log
                ProfileLog profileLog = (ProfileLog) intent.getSerializableExtra("PROFILELOG");
                new InsertLogTask(this, requestCode).execute(profileLog);
            }else if(requestCode == 233){
                LogEntry logEntry = (LogEntry) intent.getSerializableExtra("LOGENTRY");
                new InsertEntriesTask(this, requestCode).execute(logEntry);
            }else if(requestCode == 244){
                UserProfile userProfile = (UserProfile) intent.getSerializableExtra("PROFILE");
                saveProfile(userProfile);
                mUserProfile = userProfile;
                refreshMenu();
            }else if(requestCode == 255){
                DoctorRequestMessage request = (DoctorRequestMessage) intent.getSerializableExtra("DOCTORREQUEST");
                String data = mGson.toJson(request);
                mUserProfile.doctorId = request.doctorID;
                mUserProfile.isDoctorSetup = true;
                saveProfile(mUserProfile);
                new SendQueueTask("DOCTOR_REQUEST", request.doctorID).execute(data);
                finish();
                startActivity(this.getIntent());
            }
        }
    }

    private void refreshMenu() {
        Menu menu = mNavigationView.getMenu();
        if(mUserProfile != null) {
            if (mUserProfile.isProfileSetup) {
                menu.setGroupVisible(R.id.group_doctor, true);
                menu.setGroupVisible(R.id.group_profile_general, true);
                menu.setGroupVisible(R.id.group_profile_setup, false);
                if (mUserProfile.isDoctorSetup) {
                    menu.setGroupVisible(R.id.group_doctor_general, true);
                    menu.setGroupVisible(R.id.group_doctor_setup, false);
                } else {
                    menu.setGroupVisible(R.id.group_doctor_general, false);
                    menu.setGroupVisible(R.id.group_doctor_setup, true);
                }
            } else {
                menu.setGroupVisible(R.id.group_doctor, false);
                menu.setGroupVisible(R.id.group_profile_general, false);
                menu.setGroupVisible(R.id.group_profile_setup, true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteractionListener(ProfileLog item, int position) {
        MyProfile.getInstance().setActiveLog(position);
        mNavController.navigate(R.id.nav_summery);
        mDrawer.closeDrawers();
    }

    @Override
    public void onListFragmentInteractionListener(LogEntry item) {
        Intent intent = new Intent(this, EntryViewActivity.class);
        intent.putExtra("LOGENTRY", item);
        startActivity(intent);
    }

    public void startNewEntryActivity(View view) {
        Intent intent = new Intent(this, CreationEntryActivity.class);
        startActivityForResult(intent, 233);
    }

    public void startNewLogActivity(View view) {
        Intent intent = new Intent(this, CreationLogActivity.class);
        startActivityForResult(intent, 222);
    }

    public void startProfileCreationActivity(MenuItem menuItem){
        Intent intent = new Intent(this, CreationProfileActivity.class);
        startActivityForResult(intent, 244);
    }

    public void startDoctorCreationActivity(MenuItem menuItem){
        Intent intent = new Intent(this, CreationDoctorActivity.class);
        intent.putExtra("PROFILE", mUserProfile);
        startActivityForResult(intent, 255);
    }

    @Override
    public void onTaskComplete(int responseCode) {
        if(responseCode == 222){ // inserting log
            finish();
            startActivity(getIntent());
        }else if(responseCode == 233){
            finish();
            startActivity(getIntent());
        }
    }

}
