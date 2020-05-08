package com.medco.mymedicallog.ui.main;


import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.entities.AccountDetails;
import com.medco.mymedicallog.entities.DoctorDetails;
import com.medco.mymedicallog.entities.PatientDetails;
import com.medco.mymedicallog.ui.fragments.DoctorFragment;
import com.medco.mymedicallog.ui.fragments.EntriesFragment;
import com.medco.mymedicallog.ui.fragments.EntryFragment;
import com.medco.mymedicallog.ui.fragments.PatientFragment;
import com.medco.mymedicallog.ui.interfaces.OnDoctorInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainDoctorFragment extends Fragment {


    private OnDoctorInteractionListener mListener;
    private List<LogEntry> mEntries;
    private DoctorDetails mDoctorDetails;
    private AccountDetails mAccountDetails;
    private PatientDetails mPatientDetails;

    private ImageButton mControlUpload;
    private ImageButton mControlDownload;
    private ImageButton mControlSync;
    private ImageButton mControlDelete;
    private View mControlView;
    private LogEntry mSelectedEntry;

    public MainDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null) {
            mDoctorDetails = (DoctorDetails) this.getArguments().getSerializable("DOCTOR_DETAILS");
            mAccountDetails = (AccountDetails) this.getArguments().getSerializable("ACCOUNT_DETAILS");
            mPatientDetails = (PatientDetails) this.getArguments().getSerializable("PATIENT_DETAILS");
            List<LogEntry> entries = (List<LogEntry>) this.getArguments().getSerializable("LOG_ENTRIES");
            mEntries = new ArrayList<>();
            for(LogEntry entry : entries){
                if(!entry.doctorUploaded){
                    mEntries.add(entry);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_doctor, container, false);

        mControlUpload =  view.findViewById(R.id.control_upload_btn);
        mControlDownload = view.findViewById(R.id.control_backup_btn);
        mControlSync =   view.findViewById(R.id.control_sync_btn);
        mControlDelete = view.findViewById(R.id.control_delete_btn);
        mControlView = view.findViewById(R.id.content_controls);


        mControlSync.setOnClickListener(e->{
            mListener.onDoctorSyncInteraction();
        });

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if(mDoctorDetails != null){
            if(!mDoctorDetails.isRequestPending && mDoctorDetails.isConnected){
                mControlView.setVisibility(View.VISIBLE);
                fragmentTransaction.add(R.id.fragment_container, EntryFragment.newInstance(mSelectedEntry,true, R.string.text_entry_title_current));
                fragmentTransaction.add(R.id.fragment_container, EntriesFragment.newInstance(mEntries, "Not Uploaded"));
                fragmentTransaction.add(R.id.fragment_container, PatientFragment.newInstance(mPatientDetails));
            }
            fragmentTransaction.add(R.id.fragment_container, DoctorFragment.newInstance(mDoctorDetails, false));
        }
        fragmentTransaction.commit();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDoctorInteractionListener) {
            mListener = (OnDoctorInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDoctorInteractionListener");
        }
    }

    //@Override
    //public void onClick(View v) {
    //    if(v == mUploadBtn) {
    //        mListener.onUploadEntriesInteraction(mEntries);
    //        mEntries.clear();
//
    //        FragmentManager fragmentManager = getParentFragmentManager();
    //        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    //        fragmentTransaction.add(R.id.fragment_container, EntriesFragment.newInstance(mEntries, "Not Uploaded"));
    //        fragmentTransaction.commit();
    //    }
    //}
}
