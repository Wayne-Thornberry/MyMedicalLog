package com.medco.mymedicallog.ui.main.logs;

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
import com.medco.mymedicallog.adapters.ProfileLogListRecyclerViewAdapter;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.interfaces.OnProfileLogListFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;
import com.medco.mymedicallog.tasks.GetLogsTask;

import java.util.ArrayList;

public class LogSelectionFragment extends Fragment implements OnProfileLogListFragmentInteractionListener, OnTaskCompleteListener {

    private OnProfileLogListFragmentInteractionListener mListener;
    private RecyclerView mListView;

    public LogSelectionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_logs, container, false);
        new GetLogsTask(this.getActivity(), this).execute();
        mListView = view.findViewById(R.id.log_list);
        mListView.setAdapter(new ProfileLogListRecyclerViewAdapter(new ArrayList<ProfileLog>(), mListener));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileLogListFragmentInteractionListener) {
            mListener = (OnProfileLogListFragmentInteractionListener) context;
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

    @Override
    public void onListFragmentInteractionListener(ProfileLog item, int position) {
        if(mListener != null)
            mListener.onListFragmentInteractionListener(item, position);
    }

    @Override
    public void onTaskComplete(int responseCode) {
        mListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mListView.setAdapter(new ProfileLogListRecyclerViewAdapter(MyProfile.getInstance().getLogs(), mListener));
    }
}
