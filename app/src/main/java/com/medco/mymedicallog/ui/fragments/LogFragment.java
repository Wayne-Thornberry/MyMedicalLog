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
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.ui.interfaces.OnLogInteractionListener;

import java.util.Date;


public class LogFragment extends Fragment {

    private OnLogInteractionListener mListener;
    private ProfileLog mLog;
    private boolean mClickable;
    private int mTitle;

    private ImageView mErrorImage;
    private TextView mErrorHeading;
    private TextView mErrorText;
    private TextView mErrorButton;

    private TextView mLogStatus;
    private TextView mLogCount;
    private TextView mLogUpdated;
    private TextView mLogCreated;
    private TextView mLogName;
    private TextView mLogSeverity;
    private TextView mLogTitle;

    private View mErrorView;
    private View mMainView;
    private View mControlView;
    private int mSize;
    private LogEntry mEntry;
    private boolean mUploaded;

    public LogFragment() {

    }

    public static LogFragment newInstance(ProfileLog log, LogEntry entry, boolean clickable, boolean uploaded, int title, int size) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putSerializable("LOG", log);
        args.putSerializable("ENTRY", entry);
        args.putBoolean("CLICKABLE", clickable);
        args.putBoolean("UPLOADED", uploaded);
        args.putInt("TITLE", title);
        args.putInt("SIZE", size);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLog = (ProfileLog) this.getArguments().getSerializable("LOG");
            mEntry = (LogEntry) this.getArguments().getSerializable("ENTRY");
            mClickable =   this.getArguments().getBoolean("CLICKABLE");
            mUploaded =   this.getArguments().getBoolean("UPLOADED");
            mTitle = this.getArguments().getInt("TITLE");
            mSize = this.getArguments().getInt("SIZE");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogInteractionListener) {
            mListener = (OnLogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLogInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        mLogStatus = view.findViewById(R.id.log_status);
        mLogCount = view.findViewById(R.id.log_count);
        mLogUpdated = view.findViewById(R.id.log_updated);
        mLogCreated = view.findViewById(R.id.log_created);
        mLogName = view.findViewById(R.id.log_name);
        mLogSeverity = view.findViewById(R.id.log_severity);
        mLogTitle = view.findViewById(R.id.log_title);

        mErrorImage = view.findViewById(R.id.notice_image);
        mErrorHeading = view.findViewById(R.id.notice_heading);
        mErrorText = view.findViewById(R.id.notice_text);
        mErrorButton = view.findViewById(R.id.notice_btn);


        mErrorView = view.findViewById(R.id.content_error);
        mMainView = view.findViewById(R.id.content_log);
        mControlView = view.findViewById(R.id.content_controls);

        if(mLog == null){
            mErrorImage.setVisibility(View.GONE);
            mErrorHeading.setText(R.string.text_notice_no_log_heading);
            mErrorText.setText(R.string.text_notice_no_log_text);
            mErrorButton.setText(R.string.text_notice_no_log_btn);
            mErrorButton.setOnClickListener(e->{
                mListener.onLogErrorInteraction();
            });
        }else{
            mErrorView.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);
            mControlView.setVisibility(View.GONE);

            mLogTitle.setText(getResources().getString(mTitle));
            mLogSeverity.setText(mLogSeverity.getText() + ": " + getResources().getString(R.string.text_entry_severity_none));
            if(mUploaded){
                mLogStatus.setText(mLogStatus.getText() + ": " + getResources().getString(R.string.text_status_uploaded));
            }else{
                mLogStatus.setText(mLogStatus.getText() + ": " + getResources().getString(R.string.text_status_not_uploaded));
            }

            if(mEntry != null){
                mLogSeverity.setText(getResources().getString(R.string.text_entry_severity) + ": " + mEntry.painSeverity);
            }

            mLogCount.setText(mLogCount.getText() + ": " + mSize);
            mLogUpdated.setText(mLogUpdated.getText() + ": " + new Date(mLog.logUpdatedDate).toString());
            mLogCreated.setText(mLogCreated.getText() +  ": " + new Date(mLog.logCreatedDate).toString());
            mLogName.setText(mLogName.getText() + ": " + mLog.logName);

            if(mClickable){
                view.findViewById(R.id.log).setOnClickListener(e->{
                    mListener.onLogInteraction(mLog);
                });
            }
        }

        return view;
    }

}
