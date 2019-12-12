package com.mymedicallog.core.activities;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mymedicallog.core.R;
import com.mymedicallog.core.fragments.MedicalDisplayFragment;
import com.mymedicallog.core.fragments.MedicalEntryViewFragment;
import com.mymedicallog.core.fragments.MedicalLogFragment;
import com.mymedicallog.core.interfaces.OnFragmentInteractionListener;
import com.mymedicallog.core.interfaces.OnListFragmentInteractionListener;
import com.mymedicallog.core.objects.DummyItem;

import java.util.Objects;

public class PresentationActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnListFragmentInteractionListener {

    private int mViewDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);


        FloatingActionButton fab = findViewById(R.id.fab);
        try {
            int fragmentToLoad = Objects.requireNonNull(getIntent().getExtras()).getInt("loadFragment");
            switchView(fragmentToLoad);
        }catch (NullPointerException e){
            // ignore
        }
    }

    private void switchView(int fragmentToLoad) {
        switch (fragmentToLoad){
            case 1:
                MedicalLogFragment medicalLogFragment = MedicalLogFragment.newInstance(0);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, medicalLogFragment)
                        .commitNow();
                mViewDisplayed = 1;
                break;
            default :
                MedicalDisplayFragment medicalDisplayFragment = MedicalDisplayFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, medicalDisplayFragment)
                        .commitNow();
                mViewDisplayed = 2;
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_presentation, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void switchView(MenuItem item) {
        // if this button is pressed, switch between a list view and presentation view
        if(mViewDisplayed == 1){
            switchView(2);
        }else{
            switchView(1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyItem item) {
        MedicalEntryViewFragment medicalEntryViewFragment = MedicalEntryViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, medicalEntryViewFragment)
                .commitNow();
        mViewDisplayed = 3;
    }

    public void viewProfile(MenuItem item) {
        Intent myIntent = new Intent(this, ProfileActivity.class);
        startActivity(myIntent);
    }

    public void startRecordingEntry(View view) {
        Intent myIntent = new Intent(this, GatheringActivity.class);
        startActivity(myIntent);
    }
}
