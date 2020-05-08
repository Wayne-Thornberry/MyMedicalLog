package com.medco.mymedicallog.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.navigation.ui.NavigationUI;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.medco.mymedicallog.ui.creation.CreateActivity;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.ViewActivity;
import com.medco.mymedicallog.data.messages.ConnectionRequestMessage;
import com.medco.mymedicallog.data.messages.DoctorSyncMessage;
import com.medco.mymedicallog.entities.DoctorCreation;
import com.medco.mymedicallog.data.GeneralData;
import com.medco.mymedicallog.entities.AccountCreation;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.entities.AccountDetails;
import com.medco.mymedicallog.entities.DoctorDetails;
import com.medco.mymedicallog.entities.PatientDetails;
import com.medco.mymedicallog.tasks.SendQueueTask;
import com.medco.mymedicallog.ui.interfaces.OnEntriesInteractionListener;
import com.medco.mymedicallog.ui.interfaces.*;
import com.medco.mymedicallog.tasks.*;
import com.medco.mymedicallog.ui.login.LoginActivity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.medco.mymedicallog.ui.creation.CreateActivity.*;
import static com.medco.mymedicallog.database.MainDatabase.*;

public class MainActivity extends AppCompatActivity implements OnEntriesInteractionListener, OnPatientInteractionListener, OnTaskFinishedListener, OnHomeInteractionListener, OnLogInteractionListener, OnEntryInteractionListener, OnDoctorInteractionListener, OnLogsInteractionListener, NavController.OnDestinationChangedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private List<LogEntry> mLogEntries;
    private List<ProfileLog> mProfileLogs;

    private DoctorDetails mDoctorDetails;
    private PatientDetails mPatientDetails;
    private AccountDetails mAccountDetails;

    private ProfileLog mRecentLog;
    private LogEntry mRecentEntry;
    private String mUUID;

    private boolean mLoggedIn = false;
    private View mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Run", "Activity");
        setContentView(R.layout.activity_main);
        load();

        if(mAccountDetails.isSetup && !mLoggedIn) {
            startLoginActivity();
        }
        mError = findViewById(R.id.account_not_setup);
        mError.setVisibility(View.GONE);
        if(!mAccountDetails.isSetup) {
            mError.setVisibility(View.VISIBLE);
            mError.setOnClickListener(e->{
                startCreateActivity(CREATE_ACCOUNT_DATA_CODE);
            });
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        BottomNavigationView mNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        Bundle bundle = createBundle();
        navController.setGraph(R.navigation.navigation_general, bundle);
        navController.addOnDestinationChangedListener(this);

        NavigationUI.setupWithNavController(mNavigationView, navController);
        mNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("ACCOUNT_DATA", mAccountDetails);
        startActivityForResult(intent, 23333);
    }

    private void load() {
        Gson gson = new Gson();
        try {
            String data = new ReadFromFileTask(this, "GeneralData").execute().get();
            GeneralData generalData = gson.fromJson(data, GeneralData.class);
            mUUID = generalData.uuid;
            mDoctorDetails = new DoctorDetails();
            mDoctorDetails.id = generalData.doctorId;
            mDoctorDetails.name = generalData.doctorName;
            mDoctorDetails.queue = "DOCTOR-ID:" + generalData.doctorId + "-QUEUE";
            mDoctorDetails.isConnected = generalData.isDoctorConnected;
            mDoctorDetails.isRequestPending = generalData.isDoctorRequestPending;
            mDoctorDetails.lastOnline = generalData.doctorLastOnline;
            mDoctorDetails.status = generalData.doctorStatus;

            mAccountDetails = new AccountDetails();
            mAccountDetails.username = generalData.accountUsername;
            mAccountDetails.email = generalData.accountEmail;
            mAccountDetails.password = generalData.accountPassword;
            mAccountDetails.isSetup = generalData.isAccountSetup;

            mPatientDetails = new PatientDetails();
            mPatientDetails.id = generalData.patientId;
            mPatientDetails.name = generalData.patientName;
            mPatientDetails.dob = generalData.patientDob;
            mPatientDetails.age = generalData.patientAge;
            mPatientDetails.ppsno = generalData.patientPPSNo;
            mPatientDetails.queue = "USER-ID:" + generalData.uuid + "-QUEUE";

            mProfileLogs = (List<ProfileLog>) new DatabaseTask(this, SELECT_LOG_CODE).execute().get();
            mLogEntries = (List<LogEntry>) new DatabaseTask(this, SELECT_ENTRY_CODE).execute().get();

            if(mProfileLogs.size() > 0)
                mRecentLog = mProfileLogs.get(mProfileLogs.size() -1);
            if(mLogEntries.size() > 0)
                mRecentEntry = mLogEntries.get(mLogEntries.size() - 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Resumed", "Wow");
        load();
        refreshCurrentFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == -1){
            try {
                Log.e("Activity Result", requestCode + " " + resultCode);
                if(requestCode == CREATE_LOG_CODE){ // inserting log
                    ProfileLog log = (ProfileLog) intent.getSerializableExtra("CREATION_LOG");
                    if(log == null) return;
                    log.logCreatedDate = new Date().getTime();
                    new DatabaseTask(this, INSERT_LOG_CODE).execute(log).get();
                    mProfileLogs = (List<ProfileLog>) new DatabaseTask(this, SELECT_LOG_CODE).execute().get();
                }else if(requestCode == CREATE_ACCOUNT_DATA_CODE || requestCode == CREATE_DOCTOR_DATA_CODE){ // General data modification
                    Gson gson = new Gson();
                    String data = new ReadFromFileTask(this, "GeneralData").execute().get();
                    GeneralData generalData = gson.fromJson(data, GeneralData.class);
                    if(requestCode == CREATE_ACCOUNT_DATA_CODE){
                        AccountCreation accountCreation = (AccountCreation) intent.getSerializableExtra("CREATION_USER_DATA");
                        if(accountCreation == null) return;
                        generalData.accountUsername = accountCreation.username;
                        generalData.accountPassword = accountCreation.password;
                        generalData.accountPassword = accountCreation.email;
                        generalData.isAccountSetup = true;
                        mError.setVisibility(View.GONE);
                    }else if(requestCode == CREATE_DOCTOR_DATA_CODE) {
                        DoctorCreation doctorCreation = (DoctorCreation) intent.getSerializableExtra("CREATION_DOCTOR_DATA");
                        if (doctorCreation == null) return;
                        generalData.isDoctorRequestPending = true;
                        generalData.patientPPSNo = doctorCreation.ppsno;
                        generalData.doctorId = doctorCreation.doctorID;
                        generalData.doctorPasscode = doctorCreation.passcode;

                        ConnectionRequestMessage conRequest = new ConnectionRequestMessage();
                        conRequest.ppsno = doctorCreation.ppsno;
                        conRequest.doctorID = doctorCreation.doctorID;
                        conRequest.reason = "";
                        conRequest.approved = false;
                        conRequest.uuid = generalData.uuid;
                        String queue = "DOCTOR-ID:" + doctorCreation.doctorID + "-QUEUE";
                        new SendQueueTask(mUUID, "USER_CONNECTION_REQUEST", queue).execute(new Gson().toJson(conRequest));
                    }
                    new WriteToFileTask(this, "GeneralData").execute(generalData);
                }else if(requestCode == 23333){
                    mLoggedIn = true;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            refreshCurrentFragment();
        }else if(resultCode == 0){
            if(requestCode == 23333){
                finish();
            }
        }
    }

    private void refreshCurrentFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        int id = navController.getCurrentDestination().getId();
        navController.popBackStack();
        Bundle bundle = createBundle();
        navigateTo(id, bundle, null);
    }

    private Bundle createBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("DOCTOR_DETAILS", mDoctorDetails);
        bundle.putSerializable("ACCOUNT_DETAILS", mAccountDetails);
        bundle.putSerializable("PATIENT_DETAILS", mPatientDetails);
        bundle.putSerializable("PROFILE_LOGS", (Serializable) mProfileLogs);
        bundle.putSerializable("LOG_ENTRIES", (Serializable) mLogEntries);
        bundle.putSerializable("RECENT_LOG", mRecentLog);
        bundle.putSerializable("RECENT_ENTRY", mRecentEntry);
        return bundle;
    }


    /* LOGS INTERACTION */
    @Override
    public void onLogsLogInteraction(ProfileLog log) {
        startViewActivity(log, 2);
    }

    @Override
    public void onNewLogInteraction() {
        startCreateActivity(CREATE_LOG_CODE);
    }

    /* CONTROLS */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        load();
        Bundle bundle = createBundle();
        navigateTo(item.getItemId(), bundle, item);
        return true;
    }

    private void navigateTo(int id, Bundle extras, MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavOptions.Builder builder = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
        if(item != null){
            if ((item.getOrder() & Menu.CATEGORY_SECONDARY) == 0) {
                builder.setPopUpTo(findStartDestination(navController.getGraph()).getId(), false);
            }
        }
        navController.navigate(id, extras, builder.build());
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

    }

    static NavDestination findStartDestination(@NonNull NavGraph graph) {
        NavDestination startDestination = graph;
        while (startDestination instanceof NavGraph) {
            NavGraph parent = (NavGraph) startDestination;
            startDestination = parent.findNode(parent.getStartDestination());
        }
        return startDestination;
    }


    @Override
    public void onUploadEntriesInteraction(List<LogEntry> entries) {
        Gson gson = new Gson();
        String data = gson.toJson(entries);
        new DatabaseTask(this, UPDATE_ENTRY_CODE).execute(entries.toArray());
        new SendQueueTask(mUUID, "LOG", mDoctorDetails.queue).execute(data);
    }

    @Override
    public void onDoctorInteraction() {
        navigateTo(R.id.nav_doctor, createBundle(), null);
    }

    @Override
    public void onAccountErrorInteraction() {
        startCreateActivity(CREATE_ACCOUNT_DATA_CODE);
    }

    @Override
    public void onDoctorErrorInteraction() {
        startCreateActivity(CREATE_DOCTOR_DATA_CODE);
    }

    @Override
    public void onDoctorSyncInteraction() {
        new DoctorSyncMessage();
        new SendQueueTask(mUUID, "DOCTOR_SYNC", mDoctorDetails.queue).execute();
    }

    @Override
    public void onLogErrorInteraction() {
        startCreateActivity(CREATE_LOG_CODE);
    }


    @Override
    public void onEntryErrorInteraction() {
        if(mRecentLog != null)
            startViewActivity(mRecentLog, 2);
        else{
            startCreateActivity(CREATE_LOG_CODE);
        }
    }

    @Override
    public void onLogEntryInteraction(LogEntry entry) {
        startViewActivity(entry, 1);
    }

    private void startCreateActivity(int creationCode) {
        Intent intent = new Intent(this, CreateActivity.class);
        intent.putExtra("REQUEST_CODE", creationCode);
        intent.putExtra("PATIENT_PPSNO", mPatientDetails.ppsno);
        intent.putExtra("DOCTOR_ID", mDoctorDetails.id);
        startActivityForResult(intent, creationCode);
    }

    private void startViewActivity(Serializable serializable, int viewing) {
        Intent intent = new Intent(this, ViewActivity.class);
        if(viewing == 2) {
            intent.putExtra("LOG", serializable);
        }else if(viewing == 1){
            intent.putExtra("ENTRY", serializable);
        }
        intent.putExtra("VIEWING", viewing);
        intent.putExtra("UUID", mUUID);
        intent.putExtra("DOCTOR_DETAILS", mDoctorDetails);
        startActivity(intent);
    }

    @Override
    public void onEntryInteraction(LogEntry entry) {
        startViewActivity(entry, 1);
    }

    @Override
    public void onLogInteraction(ProfileLog log) {
        startViewActivity(log, 2);
    }
}
