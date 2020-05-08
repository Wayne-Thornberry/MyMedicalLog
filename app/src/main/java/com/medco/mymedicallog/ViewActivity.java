package com.medco.mymedicallog;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.medco.mymedicallog.data.CustomDataEntry;
import com.medco.mymedicallog.data.messages.ProfileLogAndEntriesMessage;
import com.medco.mymedicallog.data.messages.LogEntryMessage;
import com.medco.mymedicallog.data.messages.ProfileLogMessage;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.entities.DoctorDetails;
import com.medco.mymedicallog.tasks.DatabaseTask;
import com.medco.mymedicallog.tasks.SendQueueTask;
import com.medco.mymedicallog.ui.adapters.SectionsPagerAdapter;
import com.medco.mymedicallog.ui.creation.CreateActivity;
import com.medco.mymedicallog.ui.fragments.EntriesFragment;
import com.medco.mymedicallog.ui.fragments.EntryFragment;
import com.medco.mymedicallog.ui.fragments.LogFragment;
import com.medco.mymedicallog.ui.fragments.PainFragment;
import com.medco.mymedicallog.ui.interfaces.*;
import com.medco.mymedicallog.ui.views.AvatarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.medco.mymedicallog.ui.creation.CreateActivity.CREATE_ENTRY_CODE;
import static com.medco.mymedicallog.database.MainDatabase.*;

public class ViewActivity extends AppCompatActivity implements OnEntriesInteractionListener, OnTaskFinishedListener, OnLogInteractionListener, OnEntryInteractionListener, OnAvatarInteractionListener, View.OnClickListener {

    private ProfileLog mLog;
    private LogEntry mEntry;

    private ViewPager mViewPager;
    private AvatarView mAvatarView;
    private AnyChartView mAnyChartView;
    private Cartesian mCartesian;

    private ArrayList<LogEntry> mEntries;
    private ArrayList<String> mPainLocations;

    private DoctorDetails mDoctorDetails;
    private boolean mEnableControls = true;
    private FloatingActionButton mFab;

    private ImageButton mUploadButton;
    private ImageButton mBackupButton;
    private ImageButton mDeleteButton;
    private View mControlView;
    private int mViewing;
    private LogEntry mRecentEntry;
    private Toolbar mToolbar;
    private String mUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mAnyChartView = new AnyChartView(this);
        mAvatarView = new AvatarView(this);
        mAvatarView.setOnAvatarInteraction(this);
        mFab = findViewById(R.id.fab_new_entry);
        mFab.setOnClickListener(e->{
            startCreationActivity();
        });

        mViewing = this.getIntent().getIntExtra("VIEWING", 0);
        mDoctorDetails = (DoctorDetails) this.getIntent().getSerializableExtra("DOCTOR_DETAILS");
        mUUID = this.getIntent().getStringExtra("UUID");
        if(mViewing == 1) {
            mEntry = (LogEntry) this.getIntent().getSerializableExtra("ENTRY");
        }else if(mViewing == 2){
            mLog = (ProfileLog) this.getIntent().getSerializableExtra("LOG");
        }

        SectionsPagerAdapter adapter = new SectionsPagerAdapter();
        adapter.addFragment(mAvatarView, "");
        adapter.addFragment(mAnyChartView, "");
        mViewPager = this.findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mViewPager, true);



        mUploadButton = findViewById(R.id.control_upload_btn);
        mBackupButton = findViewById(R.id.control_backup_btn);
        mDeleteButton = findViewById(R.id.control_delete_btn);

        mControlView = this.findViewById(R.id.content_controls);
        mControlView.setVisibility(View.GONE);
        if(mEnableControls) {
            mControlView.setVisibility(View.VISIBLE);
            if(mDoctorDetails.isConnected) {
                mUploadButton.setVisibility(View.VISIBLE);
                mUploadButton.setOnClickListener(this);
                mUploadButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                mBackupButton.setVisibility(View.VISIBLE);
                mBackupButton.setOnClickListener(e -> {
                });

            }
            mDeleteButton.setVisibility(View.VISIBLE);
            mDeleteButton.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStuff();
        refresh();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == -1){
            Log.e("Activity Result", requestCode + " " + resultCode);
            if(requestCode == CREATE_ENTRY_CODE){
                Object object = intent.getSerializableExtra("CREATION_ENTRY");
                if(object == null) return;
                try {
                    new DatabaseTask(this, INSERT_ENTRY_CODE).execute(object).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if(resultCode == 0){

        }
    }


    private void updateStuff() {
        try {
            mEntries = new ArrayList<>();
            if(mViewing == 1) {
                ArrayList<LogEntry> entries = (ArrayList<LogEntry>) new DatabaseTask(this, SELECT_ENTRY_CODE).execute(mEntry.entryLogId).get();
                for (LogEntry entry : entries){
                    if(entry.entryId == mEntry.entityParentId){
                        mEntries.add(entry);
                    }
                }
                //if (mEntries.size() > 0) {
                //    mRecentEntry = mEntries.get(mEntries.size() - 1);
                //}
            } else if(mViewing == 2) {
                mEntries = (ArrayList<LogEntry>) new DatabaseTask(this, SELECT_ENTRY_CODE).execute(mLog.logId).get();
                if (mEntries.size() > 0) {
                    mRecentEntry = mEntries.get(mEntries.size() - 1);
                }
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        mPainLocations = new ArrayList<>();
        boolean uploaded = true;
        if(mViewing == 2){
            for (LogEntry entry : mEntries){
                if(!entry.doctorUploaded)
                    uploaded = false;
                if(entry.painLocation == null) continue;
                String[] locations = entry.painLocation.split(" ");
                for (String location : locations){
                    if(!mPainLocations.contains(location)) {
                        mPainLocations.add(location);
                    }
                }
            }
        }else if(mViewing == 1){
            String[] locations = mEntry.painLocation.split(" ");
            for (String location : locations){
                if(!mPainLocations.contains(location)) {
                    mPainLocations.add(location);
                }
            }
        }

        mAvatarView.setHighlightZones(mPainLocations);
        mAvatarView.setView(0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (Fragment fragment : fragmentManager.getFragments()) {
            fragmentTransaction.remove(fragment);
        }


        mCartesian = AnyChart.line();
        mCartesian.title("Severity Trend");
        if(mEntries != null){
            if(mEntries.size() > 0){

                ArrayList<DataEntry> dataSet = new ArrayList<>();

                mCartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                mCartesian.yAxis(0).title("Severity");
                mCartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                Set set = Set.instantiate();
                for (int i = 0; i < mEntries.size(); i++) {
                    int severity = 0;
                    LogEntry entry = mEntries.get(i);
                    if(entry.painSeverity.equals("Mild")){
                        severity = 0;
                    }else if(entry.painSeverity.equals("Moderate")){
                        severity = 5;
                    }else if(entry.painSeverity.equals("Severe")){
                        severity = 10;
                    }
                    dataSet.add(new CustomDataEntry(i + "", severity));
                }
                set.data(dataSet);
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

                Line series1 = mCartesian.line(series1Mapping);
                series1.name("Severity");
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);
            }
        }

        mAnyChartView.setChart(mCartesian);
        if(mViewing == 1){
            if(!mEntry.entryType.equals("CHILD")){
                fragmentTransaction.add(R.id.fragment_container, EntriesFragment.newInstance(mEntries, "Update Entries"));
            }
            fragmentTransaction.add(R.id.fragment_container, EntryFragment.newInstance(mEntry,false, R.string.text_entry_title_current));
            fragmentTransaction.add(R.id.fragment_container, PainFragment.newInstance(mEntry));
        }else if(mViewing == 2) {
            fragmentTransaction.add(R.id.fragment_container, EntriesFragment.newInstance(mEntries, "Log " + mLog.logName));
            fragmentTransaction.add(R.id.fragment_container, LogFragment.newInstance(mLog, mRecentEntry, false, uploaded, R.string.text_log_title_current, mEntries.size()));
            fragmentTransaction.add(R.id.fragment_container, EntryFragment.newInstance(mRecentEntry,true, R.string.text_entry_title_recent));
        }
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mEntries = null;
        mLog = null;
    }

    @Override
    public void onClick(View v) {
        if(v == mUploadButton){
            Gson gson = new Gson();
            if(mViewing == 1 || mViewing == 2){
                ProfileLogMessage profileLogMessage = mLog.getMessage();
                List<LogEntryMessage> logEntryMessages = new ArrayList<>();
                for (LogEntry entry : mEntries){
                    if(!entry.doctorUploaded){
                        entry.doctorUploaded = true;
                        logEntryMessages.add(entry.getMessage());
                    }
                }
                if(logEntryMessages.size() > 0) {
                    new DatabaseTask(this, UPDATE_ENTRY_CODE).execute(mEntries.toArray());
                    ProfileLogAndEntriesMessage profileLogAndEntriesMessage = new ProfileLogAndEntriesMessage();
                    profileLogAndEntriesMessage.logEntryMessages = logEntryMessages;
                    profileLogAndEntriesMessage.profileLogMessage = profileLogMessage;
                    String data = gson.toJson(profileLogAndEntriesMessage);
                    new SendQueueTask(mUUID, "LOG_AND_ENTRIES", mDoctorDetails.queue).execute(data);
                }}
        }else if(v == mDeleteButton){
            if(mViewing == 1){
                new DatabaseTask(this, DELETE_ENTRY_CODE).execute(mEntry);
                finish();
            }else if(mViewing == 2){
                new DatabaseTask(this, DELETE_LOG_CODE).execute(mLog);
                finish();
            }
        }
    }

    @Override
    public void onEntryInteraction(LogEntry entry) {
        startViewActivity(entry, 1);
    }

    @Override
    public void onLogInteraction(ProfileLog log) {
        startViewActivity(log, 2);
    }

    @Override
    public void onEntryErrorInteraction() {
        startCreationActivity();
    }

    public void startCreationActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        intent.putExtra("REQUEST_CODE", CREATE_ENTRY_CODE);
        if(mViewing == 1){
            intent.putExtra("LOG_ID", mEntry.entryLogId);
            intent.putExtra("ENTRY", mEntry);
            intent.putExtra("ENTRY_TYPE", "CHILD");
        }else if(mViewing == 2){
            intent.putExtra("LOG_ID", mLog.logId);
            intent.putExtra("ENTRY_TYPE", "PARENT");
        }
        startActivityForResult(intent, CREATE_ENTRY_CODE);
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
    public void onLogErrorInteraction() {
        // ignored
    }


    @Override
    public void onAvatarCellInteraction(String tag, boolean selected) {
        // ignored
    }

    @Override
    public void onLogEntryInteraction(LogEntry entry) {
        startViewActivity(entry, 1);
    }
}
