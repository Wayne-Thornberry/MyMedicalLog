package com.medco.mymedicallog.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.entities.AccountDetails;
import com.medco.mymedicallog.entities.DoctorDetails;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.entities.PatientDetails;
import com.medco.mymedicallog.ui.fragments.DoctorFragment;
import com.medco.mymedicallog.ui.fragments.EntryFragment;
import com.medco.mymedicallog.ui.fragments.LogFragment;
import com.medco.mymedicallog.ui.interfaces.OnHomeInteractionListener;

import java.util.List;

public class MainHomeFragment extends Fragment {

    private OnHomeInteractionListener mListener;
    private LogEntry mRecentEntry;
    private ProfileLog mRecentLog;
    private DoctorDetails mDoctorDetails;
    private AccountDetails mAccountDetails;
    private PatientDetails mPatientDetails;
    private List<LogEntry> mEntries;

    public MainHomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null) {
            mDoctorDetails = (DoctorDetails) this.getArguments().getSerializable("DOCTOR_DETAILS");
            mAccountDetails = (AccountDetails) this.getArguments().getSerializable("ACCOUNT_DETAILS");
            mPatientDetails = (PatientDetails) this.getArguments().getSerializable("PATIENT_DETAILS");
            mRecentLog = (ProfileLog) this.getArguments().getSerializable("RECENT_LOG");
            mRecentEntry = (LogEntry) this.getArguments().getSerializable("RECENT_ENTRY");
            mEntries = (List<LogEntry>) this.getArguments().getSerializable("LOG_ENTRIES");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        boolean uploaded = true;
        if(mRecentLog != null) {
            for (LogEntry entry : mEntries) {
                if (!entry.doctorUploaded && entry.entryLogId == mRecentLog.logId)
                    uploaded = false;
            }
        }else{
            uploaded = false;
        }

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, DoctorFragment.newInstance(mDoctorDetails, true));
        fragmentTransaction.add(R.id.fragment_container, LogFragment.newInstance(mRecentLog,mRecentEntry, true, uploaded, R.string.text_log_title_recent, mEntries.size()));
        fragmentTransaction.add(R.id.fragment_container, EntryFragment.newInstance(mRecentEntry,true, R.string.text_entry_title_recent));
        fragmentTransaction.commit();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeInteractionListener) {
            mListener = (OnHomeInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeInteractionListener");
        }
    }
}
