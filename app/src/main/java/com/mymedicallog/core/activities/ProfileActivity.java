package com.mymedicallog.core.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import com.mymedicallog.core.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void createEntry(MenuItem item) {
        Intent myIntent = new Intent(this, GatheringActivity.class);
        startActivity(myIntent);
    }

    public void viewLog(MenuItem item) {
        Intent myIntent = new Intent(this, PresentationActivity.class);
        myIntent.putExtra("loadFragment", 1);
        startActivity(myIntent);
    }
}
