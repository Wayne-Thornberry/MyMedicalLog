package com.example.mymedicallog;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymedicallog.lists.MyAdapter;

public class LogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Working?","Yes");
        setContentView(R.layout.activity_log);
        recyclerView = findViewById(R.id.list_log_entries);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String[] tests = new String[7];
        tests[0] = "Wow 1";
        tests[1] = "Wow 2";
        tests[2] = "Wow 3";
        tests[3] = "Wow 4";
        tests[4] = "Wow 5";
        tests[5] = "Wow 6";
        tests[6] = "Wow 7";
        mAdapter = new MyAdapter(tests);
        recyclerView.setAdapter(mAdapter);

    }
}
