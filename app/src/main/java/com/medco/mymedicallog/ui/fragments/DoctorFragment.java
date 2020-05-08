package com.medco.mymedicallog.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.entities.DoctorDetails;
import com.medco.mymedicallog.ui.interfaces.OnDoctorInteractionListener;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDoctorInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorFragment extends Fragment {

    private OnDoctorInteractionListener mListener;
    private DoctorDetails mDoctorDetails;
    private boolean mClickable;
    private boolean mEnableControls;

    private ImageView mErrorImage;
    private TextView mErrorHeading;
    private TextView mErrorText;
    private TextView mErrorButton;

    private View mErrorView;
    private View mMainView;
    private View mControlView;

    private TextView mDoctorId;
    private TextView mDoctorName;
    private TextView mDoctorStatus;
    private TextView mDoctorLocation;
    private TextView mDoctorTitle;
    private TextView mDoctorLastOnline;


    public DoctorFragment() {
        // Required empty public constructor
    }

    public static DoctorFragment newInstance(DoctorDetails doctorDetails, boolean clickable) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putSerializable("DOCTOR_DETAILS", doctorDetails);
        args.putBoolean("CLICKABLE", clickable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDoctorDetails = (DoctorDetails) getArguments().getSerializable("DOCTOR_DETAILS");
            mClickable = this.getArguments().getBoolean("CLICKABLE");
            mEnableControls = this.getArguments().getBoolean("ENABLE_CONTROLS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);

        mDoctorTitle = view.findViewById(R.id.doctor_title);
        mDoctorStatus = view.findViewById(R.id.doctor_status);
        mDoctorLocation = view.findViewById(R.id.doctor_location);
        mDoctorLastOnline = view.findViewById(R.id.doctor_last_online);

        mErrorView = view.findViewById(R.id.content_error);
        mMainView = view.findViewById(R.id.content_doctor);
        mControlView = view.findViewById(R.id.content_controls);

        mErrorImage = view.findViewById(R.id.notice_image);
        mErrorHeading = view.findViewById(R.id.notice_heading);
        mErrorText = view.findViewById(R.id.notice_text);
        mErrorButton = view.findViewById(R.id.notice_btn);

        if(!mDoctorDetails.isConnected && !mDoctorDetails.isRequestPending){
            mErrorHeading.setText(R.string.text_notice_doctor_heading);
            mErrorText.setText(R.string.text_notice_doctor_text);
            mErrorButton.setText(R.string.text_notice_doctor_btn);
            mErrorButton.setOnClickListener(e->{
                mListener.onDoctorErrorInteraction();
            });
        }else if(mDoctorDetails.isRequestPending){
            mErrorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_send_black_24dp));
            mErrorHeading.setText(R.string.text_notice_doctor_request_heading);
            mErrorText.setText(R.string.text_notice_doctor_request_text);
            mErrorButton.setText(R.string.text_notice_doctor_request_btn);
            mErrorButton.setOnClickListener(e->{
                mListener.onDoctorErrorInteraction();
            });
        }else{
            mErrorView.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);
            mControlView.setVisibility(View.GONE);

            mDoctorTitle.setText("["+ mDoctorDetails.id + "] " + getResources().getString(R.string.text_doctor_title) + ". " + mDoctorDetails.name.toUpperCase());
            if(mDoctorDetails.status == null)
                mDoctorStatus.setText(mDoctorStatus.getText() + ": " + "offline");
            else
                mDoctorStatus.setText(mDoctorStatus.getText() + ": " + mDoctorDetails.status);
            if(mDoctorDetails.lastOnline > 0) {
                mDoctorLastOnline.setText(mDoctorLastOnline.getText() + ": " + new Date(mDoctorDetails.lastOnline).toString());
            }
            mDoctorLocation.setText(mDoctorLocation.getText() + ":"  + "Kells");

            if(mClickable){
                view.findViewById(R.id.doctor).setOnClickListener(e->{
                    mListener.onDoctorInteraction();
                });
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDoctorInteractionListener) {
            mListener = (OnDoctorInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
