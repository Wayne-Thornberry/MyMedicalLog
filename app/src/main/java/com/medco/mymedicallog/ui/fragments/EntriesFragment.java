package com.medco.mymedicallog.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.ui.adapters.LogEntriesAdapter;
import com.medco.mymedicallog.ui.interfaces.OnEntriesInteractionListener;

import java.io.Serializable;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class EntriesFragment extends Fragment {

    private OnEntriesInteractionListener mListener;
    private List<LogEntry> mEntries;
    private String mFilter;
    private TextView mTextTitle;

    public static EntriesFragment newInstance(List<LogEntry> entries, String filter) {
        EntriesFragment fragment = new EntriesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ENTRIES", (Serializable) entries);
        bundle.putString("FILTER", filter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null){
            mEntries = (List<LogEntry>) this.getArguments().getSerializable("ENTRIES");
            mFilter =  this.getArguments().getString("FILTER");
        }
    }

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entries, container, false);
        mTextTitle = view.findViewById(R.id.entries_title);
        if(!mFilter.equals(""))
            mTextTitle.setText(getResources().getString(R.string.text_entries_title) + " - Filtered: " + mFilter);
        else
            mTextTitle.setText(R.string.text_entries_title);
        if(mEntries != null){
            RecyclerView listView = view.findViewById(R.id.entry_list);
            listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            listView.setAdapter(new LogEntriesAdapter(mEntries, mListener));
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEntries = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEntriesInteractionListener) {
            mListener = (OnEntriesInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnEntriesInteractionListener");
        }
    }
}