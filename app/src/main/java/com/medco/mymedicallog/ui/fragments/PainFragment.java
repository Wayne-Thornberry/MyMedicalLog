package com.medco.mymedicallog.ui.fragments;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.LogEntry;

import java.util.Date;

public class PainFragment extends Fragment {


    private LogEntry mEntry;
    private TextView mPainSeverity;
    private TextView mPainLocation;
    private TextView mPainType;
    private TextView mPainDescription;
    private TextView mPainNoticability;
    private TextView mPainStrength;
    private TextView mPainStart;

    private View mErrorView;
    private View mMainView;
    private View mControlView;

    public PainFragment() {
        // Required empty public constructor
    }

    public static PainFragment newInstance(LogEntry entry) {
        PainFragment fragment = new PainFragment();
        Bundle args = new Bundle();
        args.putSerializable("ENTRY", entry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEntry = (LogEntry) getArguments().getSerializable("ENTRY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pain, container, false);
        mPainSeverity = view.findViewById(R.id.pain_severity);
        mPainStrength = view.findViewById(R.id.pain_strength);
        mPainLocation = view.findViewById(R.id.pain_location);
        mPainType = view.findViewById(R.id.pain_type);
        mPainDescription = view.findViewById(R.id.pain_description);
        mPainNoticability = view.findViewById(R.id.pain_noticability);
        mPainStart = view.findViewById(R.id.pain_start_date);

        mErrorView = view.findViewById(R.id.content_error);
        mMainView = view.findViewById(R.id.content_pain);
        mControlView = view.findViewById(R.id.content_controls);

        if(mEntry == null){

        }else{
            mErrorView.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);
            mControlView.setVisibility(View.GONE);
            mPainSeverity.setText(mPainSeverity.getText() + ": " + mEntry.painSeverity.toUpperCase());
            mPainStrength.setText(mPainStrength.getText() + ": " + mEntry.painStrength + "/10");
            mPainLocation.setText(mPainLocation.getText() + ": " + mEntry.painLocation.replace(' ', ',').replace('_', ' ').toUpperCase());
            mPainType.setText(mPainType.getText() + ": " + mEntry.painType.toUpperCase());
            mPainDescription.setText(mPainDescription.getText() + ": " + mEntry.painDescription.toUpperCase());
            mPainNoticability.setText(mPainNoticability.getText() + ": " + mEntry.painNotice.toUpperCase());
            mPainStart.setText(mPainStart.getText() + ": " + new Date(mEntry.painStartDate));
        }

        return view;
    }
}
