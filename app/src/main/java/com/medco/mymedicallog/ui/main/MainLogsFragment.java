package com.medco.mymedicallog.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.ui.adapters.ProfileLogsAdapter;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.ui.fragments.DoctorFragment;
import com.medco.mymedicallog.ui.fragments.EntryFragment;
import com.medco.mymedicallog.ui.fragments.LogFragment;
import com.medco.mymedicallog.ui.interfaces.OnLogsInteractionListener;

import java.util.List;

public class MainLogsFragment extends Fragment {

    private OnLogsInteractionListener mListener;
    private RecyclerView mListView;
    private List<ProfileLog> mLogs;
    private FloatingActionButton mNewLogBtn;

    public MainLogsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null){
            mLogs = (List<ProfileLog>) this.getArguments().getSerializable("PROFILE_LOGS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_logs, container, false);
        mNewLogBtn = view.findViewById(R.id.fab_new_log);
        mNewLogBtn.setOnClickListener(e->{
            mListener.onNewLogInteraction();
        });
        mListView = view.findViewById(R.id.log_list);

        if(mLogs != null) {
            if(mLogs.size() > 0) {
                mListView.setNestedScrollingEnabled(false);
                mListView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
                mListView.setAdapter(new ProfileLogsAdapter(mLogs, mListener));
            }else{
                mListView.setVisibility(View.GONE);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, LogFragment.newInstance(null,null,  true,false, R.string.text_log_title_recent, 0));
                fragmentTransaction.commit();
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogsInteractionListener) {
            mListener = (OnLogsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLogSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
