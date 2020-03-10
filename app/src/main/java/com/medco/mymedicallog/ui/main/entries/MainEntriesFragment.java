package com.medco.mymedicallog.ui.main.entries;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medco.mymedicallog.MyProfile;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.adapters.LogEntryListRecyclerViewAdapter;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnLogEntryListFragmentInteractionListener;
import com.medco.mymedicallog.interfaces.OnTaskCompleteListener;
import com.medco.mymedicallog.tasks.GetLogEntriesTask;
import com.medco.mymedicallog.tasks.SendQueueTask;

import java.util.List;

import static com.medco.mymedicallog.MainActivity.mDoctorProfile;

public class MainEntriesFragment extends Fragment implements OnTaskCompleteListener {

    private OnLogEntryListFragmentInteractionListener mListener;
    private RecyclerView mListView;

    public MainEntriesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_log, container, false);
        Context context = view.getContext();
        new GetLogEntriesTask(this.getActivity(), this).execute(MyProfile.getInstance().getActiveLog().logId);
        mListView = view.findViewById(R.id.list);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogEntryListFragmentInteractionListener) {
            mListener = (OnLogEntryListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTaskComplete(int responseCode) {
        mListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        List<LogEntry> entries = MyProfile.getInstance().getEntries();
        mListView.setAdapter(new LogEntryListRecyclerViewAdapter(entries, mListener));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String entrys = gson.toJson(entries);
        Log.e("ID", mDoctorProfile.Id + "");
        new SendQueueTask("LOG_UPLOAD", mDoctorProfile.Id).execute(entrys);
    }
}
