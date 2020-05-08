package com.medco.mymedicallog.ui.creation;

import android.app.Activity;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.entities.DoctorCreation;
import com.medco.mymedicallog.entities.SelectorItem;
import com.medco.mymedicallog.entities.AccountCreation;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.ui.interfaces.OnCreationInteractionListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateActivity extends AppCompatActivity implements OnCreationInteractionListener, NavController.OnDestinationChangedListener {

    private int mRequestCode;
    private List<Integer> navigationLayouts;
    private int navigationProgress;

    public static final int CREATE_ACCOUNT_DATA_CODE = 200;
    public static final int CREATE_DOCTOR_DATA_CODE = 201;
    public static final int CREATE_LOG_CODE = 202;
    public static final int CREATE_ENTRY_CODE = 203;


    private LogEntry mLogEntry;
    private ProfileLog mProfileLog;
    private DoctorCreation mDoctorCreation;
    private AccountCreation mAccountCreation;
    private boolean mBlockProgress;
    private Toolbar mToolbar;
    private View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRequestCode = this.getIntent().getIntExtra("REQUEST_CODE", 0);
        navigationProgress = -1;
        switch (mRequestCode){
            case CREATE_DOCTOR_DATA_CODE: setupDoctor(); break;
            case CREATE_ACCOUNT_DATA_CODE: setupAccount(); break;
            case CREATE_ENTRY_CODE: setupEntry(); break;
            case CREATE_LOG_CODE: setupLog(); break;
            default: finish();
        }


        errorView = findViewById(R.id.creation_progress_error);
        errorView.setVisibility(View.GONE);
        this.findViewById(R.id.progress_next).setOnClickListener(e->{
            switch (mRequestCode){
                case CREATE_DOCTOR_DATA_CODE: this.getIntent().putExtra("CREATION_DOCTOR_DATA", mDoctorCreation);break;
                case CREATE_ACCOUNT_DATA_CODE: this.getIntent().putExtra("CREATION_USER_DATA", mAccountCreation); break;
                case CREATE_ENTRY_CODE: this.getIntent().putExtra("CREATION_ENTRY", mLogEntry); break;
                case CREATE_LOG_CODE: this.getIntent().putExtra("CREATION_LOG", mProfileLog); break;
            }
            if(!mBlockProgress) {
                errorView.setVisibility(View.GONE);
                navUp();
            }else{
                errorView.setVisibility(View.VISIBLE);
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(this);
        navController.setGraph(R.navigation.navigation_creation, this.getIntent().getExtras());
    }

    private void setupAccount() {
        mAccountCreation = new AccountCreation();
        navigationLayouts = new ArrayList<>();
        navigationLayouts.add(R.id.nav_c_profile_general);
        mToolbar.setTitle(R.string.title_activity_creation_user);
    }

    private void setupDoctor() {
        mDoctorCreation = new DoctorCreation();
        mDoctorCreation.ppsno = this.getIntent().getStringExtra("PATIENT_PPSNO");
        navigationLayouts = new ArrayList<>();
        navigationLayouts.add(R.id.nav_c_doctor_details);
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.title_activity_creation_doctor);
    }

    private void setupLog() {
        mProfileLog = new ProfileLog();
        mProfileLog.logCreatedDate = new Date().getTime();
        mProfileLog.logUpdatedDate = new Date().getTime();
        mProfileLog.logName = "My Log";
        navigationLayouts = new ArrayList<>();
        navigationLayouts.add(R.id.nav_c_log_general);

        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.title_activity_creation_log);
    }

    private void setupEntry() {
        mLogEntry = new LogEntry();
        String entryType = this.getIntent().getStringExtra("ENTRY_TYPE");
        long logId =  this.getIntent().getLongExtra("LOG_ID", 0);
        if(entryType.equals("CHILD")){
            LogEntry entry = (LogEntry) this.getIntent().getSerializableExtra("ENTRY");
            long id = entry.entryId;
            mLogEntry = entry;
            mLogEntry.entityParentId = id;
            mLogEntry.entryId = 0;
        }
        else{
            mLogEntry.entityParentId = 0;
            mLogEntry.entryLogId = logId;
            mLogEntry.painLocation = "";
            mLogEntry.entityCreatedDate = new Date().getTime();
            mLogEntry.painStartDate = new Date().getTime();
        }
        mLogEntry.entryType = entryType;

        navigationLayouts = new ArrayList<>();
        navigationLayouts.add(R.id.nav_c_entry_touch_input);
        navigationLayouts.add(R.id.nav_c_entry_review);

        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.title_activity_creation_entry);
    }

    @Override
    public void onBlockProgress(boolean blockProgress) {
        mBlockProgress = blockProgress;
        if(!mBlockProgress){
            errorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLogNameEntered(String name) {
        mProfileLog.logName = name;
    }

    @Override
    public void onUploadCheckbox(boolean isChecked) {

    }

    @Override
    public void onBackupCheckbox(boolean isChecked) {

    }

    @Override
    public void onLocationSelected(String location) {
        if(mLogEntry.painLocation == null) return;
        if(!mLogEntry.painLocation.contains(location))
            mLogEntry.painLocation += location + " ";
    }

    @Override
    public void onLocationDeSelected(String location) {
        if(mLogEntry.painLocation == null) return;
        if(mLogEntry.painLocation.contains(location))
            mLogEntry.painLocation = mLogEntry.painLocation.replace(location, "");
    }

    @Override
    public void onGeneralDetails(String username, String email, String password) {
        mAccountCreation.username = username;
        mAccountCreation.email = email;
        mAccountCreation.password = password;
    }

    @Override
    public void onDoctorDetails(String ppsno, int doctorCode, int passCode) {
        mDoctorCreation.ppsno = ppsno;
        mDoctorCreation.doctorID = doctorCode;
        mDoctorCreation.passcode = passCode;
    }

    @Override
    public void onEntityChanged(LogEntry mEntry) {
        mEntry = mLogEntry;
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {


    }

    @Override
    public void onBackPressed() {
        navDown();
    }

    private void navUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(navigationProgress < navigationLayouts.size() - 1){
            navigationProgress++;
            navController.navigate(navigationLayouts.get(navigationProgress), this.getIntent().getExtras());
        }else{
            setResult(Activity.RESULT_OK, this.getIntent());
            finish();
        }
    }

    private void navDown(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(navigationProgress > 0){
            navigationProgress--;
            navController.popBackStack();
        }else{
            finish();
        }
    }
}
