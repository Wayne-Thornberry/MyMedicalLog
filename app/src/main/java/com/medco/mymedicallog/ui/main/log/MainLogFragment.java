package com.medco.mymedicallog.ui.main.log;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.MyProfile;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.adapters.DisplayListRecyclerViewAdapter;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnDisplayListFragmentInteractionListener;

import java.util.List;

public class MainLogFragment extends Fragment {

    private static List<LogEntry> mEntries;
    private OnDisplayListFragmentInteractionListener mListener;
    private RecyclerView mListView;

    public MainLogFragment() {
    }

    public static MainLogFragment newInstance(List<LogEntry> entries){
        MainLogFragment fragment = new MainLogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        mEntries = entries;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        Context context = view.getContext();
        mListView = view.findViewById(R.id.list);
        mListView.setLayoutManager(new LinearLayoutManager(context));
        mListView.setAdapter(new DisplayListRecyclerViewAdapter(MyProfile.getInstance().getEntries(), mListener));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayListFragmentInteractionListener) {
            mListener = (OnDisplayListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
