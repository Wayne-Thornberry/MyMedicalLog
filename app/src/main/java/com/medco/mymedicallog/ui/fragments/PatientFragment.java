package com.medco.mymedicallog.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.entities.PatientDetails;
import com.medco.mymedicallog.ui.interfaces.OnPatientInteractionListener;

public class PatientFragment extends Fragment {

    private PatientDetails mPatientDetails;
    private OnPatientInteractionListener mListener;

    private ImageView mErrorImage;
    private TextView mErrorHeading;
    private TextView mErrorText;
    private TextView mErrorButton;
    private View mErrorView;
    private View mMainView;
    private View mControlView;

    private TextView mPatientName;
    private TextView mPatientAge;
    private TextView mPatientPhoneNo;
    private TextView mPatientAddress;
    private TextView mPatientPPSNO;
    private TextView mPatientTitle;

    private ImageView mControlUpload;
    private ImageView mControlDownload;
    private ImageView mControlSync;
    private ImageView mControlDelete;

    public PatientFragment() {
        // Required empty public constructor
    }

    public static PatientFragment newInstance(PatientDetails patientDetails) {
        PatientFragment fragment = new PatientFragment();
        Bundle args = new Bundle();
        args.putSerializable("PATIENT_DETAILS", patientDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPatientDetails = (PatientDetails) getArguments().getSerializable("PATIENT_DETAILS");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPatientInteractionListener) {
            mListener = (OnPatientInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPatientInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        mErrorView = view.findViewById(R.id.content_error);
        mErrorImage = view.findViewById(R.id.notice_image);
        mErrorHeading = view.findViewById(R.id.notice_heading);
        mErrorText = view.findViewById(R.id.notice_text);
        mErrorButton = view.findViewById(R.id.notice_btn);

        mPatientTitle = view.findViewById(R.id.patient_title);
        mPatientAge = view.findViewById(R.id.patient_age);
        mPatientPhoneNo = view.findViewById(R.id.patient_phone_no);
        mPatientAddress = view.findViewById(R.id.patient_addresss);


        mErrorView = view.findViewById(R.id.content_error);
        mMainView = view.findViewById(R.id.content_patient);
        mControlView = view.findViewById(R.id.content_controls);

        mControlUpload =  view.findViewById(R.id.control_upload_btn);
        mControlDownload = view.findViewById(R.id.control_backup_btn);
        mControlSync =   view.findViewById(R.id.control_sync_btn);
        mControlDelete = view.findViewById(R.id.control_delete_btn);

        if(mPatientDetails == null){
            mErrorView.setVisibility(View.VISIBLE);
            mErrorHeading.setText(R.string.text_notice_profile_heading);
            mErrorText.setText(R.string.text_notice_profile_text);
            mErrorButton.setText(R.string.text_notice_profile_btn);
            mErrorButton.setOnClickListener(e->{
                mListener.onAccountErrorInteraction();
            });
        }else{
            mErrorView.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);
            mControlView.setVisibility(View.GONE);


            mControlUpload.setVisibility(View.INVISIBLE);
            mControlDownload.setVisibility(View.INVISIBLE);
            mControlDelete.setVisibility(View.INVISIBLE);

            mPatientTitle.setText("[" + mPatientDetails.ppsno + "] "+getResources().getString(R.string.patient_title) + " " + mPatientDetails.name);
            mPatientAge.setText(mPatientAge.getText() + ": " + mPatientDetails.age);
            mPatientPhoneNo.setText(mPatientPhoneNo.getText() + ": " + "085 000 000");
            mPatientAddress.setText(mPatientAddress.getText() + ": " + "16 Vinewood Hills, LS");
        }


        return view;
    }

}
