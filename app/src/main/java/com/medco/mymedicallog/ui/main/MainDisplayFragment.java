package com.medco.mymedicallog.ui.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.ui.fragments.EntriesFragment;
import com.medco.mymedicallog.ui.views.AvatarView;
import com.medco.mymedicallog.ui.interfaces.OnAvatarInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class MainDisplayFragment extends Fragment implements OnAvatarInteractionListener {


    private ArrayList<LogEntry> mEntries;
    private AvatarView mAvatarView;

    public MainDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main_display, container, false);
        mEntries = (ArrayList<LogEntry>) this.getArguments().getSerializable("LOG_ENTRIES");
        mAvatarView = view.findViewById(R.id.avatar_view);
        List<String> painLocations = new ArrayList<>();
        for (LogEntry entry : mEntries){
            if(entry.painLocation == null) continue;
            String[] locations = entry.painLocation.split(" ");
            for (String location : locations){
                if(!painLocations.contains(location)) {
                    painLocations.add(location);
                }
            }
        }
        mAvatarView.setOnAvatarInteraction(this);
        mAvatarView.setHighlightZones(painLocations);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.entries_container, EntriesFragment.newInstance(new ArrayList<>(), ""));
        fragmentTransaction.commit();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAvatarView.setView(0);
    }


    @Override
    public void onAvatarCellInteraction(String tag, boolean selected) {
        ArrayList<LogEntry> entries = new ArrayList<>();
        if(selected) {
            for (LogEntry e : mEntries) {
                if (e.painLocation.contains(tag)) {
                    entries.add(e);
                }
            }
        }

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.entries_container, EntriesFragment.newInstance(entries, tag.replace('_', ' ').toLowerCase()));
        fragmentTransaction.commit();
    }
}
