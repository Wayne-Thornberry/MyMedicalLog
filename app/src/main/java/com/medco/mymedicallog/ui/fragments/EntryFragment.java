package com.medco.mymedicallog.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.ui.interfaces.OnEntryInteractionListener;

import java.util.Date;

public class EntryFragment extends Fragment {

    private OnEntryInteractionListener mListener;
    private LogEntry mEntry;
    private boolean mClickable;
    private int mTitle;

    private ImageButton mUploadButton;
    private ImageButton mBackupButton;
    private ImageButton mDeleteButton;

    private ImageView mErrorImage;
    private TextView mErrorHeading;
    private TextView mErrorText;
    private TextView mErrorButton;

    private TextView mEntryTitle;
    private TextView mEntryId;
    private TextView mEntryType;
    private TextView mEntrySeverity;
    private TextView mEntryCreatedDate;
    private TextView mEntryUpdatedDate;
    private View mErrorView;
    private View mMainView;
    private View mEntryAlert;

    public EntryFragment() {
        // Required empty public constructor
    }

    public static EntryFragment newInstance(LogEntry entry, boolean clickable, int title) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putSerializable("ENTRY", entry);
        args.putBoolean("CLICKABLE", clickable);
        args.putInt("TITLE", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEntry = (LogEntry) this.getArguments().getSerializable("ENTRY");
            mClickable = this.getArguments().getBoolean("CLICKABLE");
            mTitle = this.getArguments().getInt("TITLE");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEntryInteractionListener) {
            mListener = (OnEntryInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEntryInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry, container, false);

        mEntryAlert = view.findViewById(R.id.entry_alert);
        mEntryTitle = view.findViewById(R.id.entry_title);
        mEntryId = view.findViewById(R.id.entry_id);
        mEntryType = view.findViewById(R.id.entry_type);
        mEntrySeverity = view.findViewById(R.id.entry_severity);
        mEntryCreatedDate = view.findViewById(R.id.entry_created_date);
        mEntryUpdatedDate = view.findViewById(R.id.entry_updated_date);

        mErrorView = view.findViewById(R.id.content_error);
        mErrorImage = view.findViewById(R.id.notice_image);
        mErrorHeading = view.findViewById(R.id.notice_heading);
        mErrorText = view.findViewById(R.id.notice_text);
        mErrorButton = view.findViewById(R.id.notice_btn);

        mErrorView = view.findViewById(R.id.content_error);
        mMainView = view.findViewById(R.id.content_entry);


        if(mEntry == null){
            mErrorImage.setVisibility(View.GONE);
            mErrorHeading.setText(R.string.text_notice_no_entry_heading);
            mErrorText.setText(R.string.text_notice_no_entry_text);
            mErrorButton.setText(R.string.text_notice_no_entry_btn);
            mErrorButton.setOnClickListener(e->{
                mListener.onEntryErrorInteraction();
            });
        }else{
            mErrorView.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);

            if(mEntry.doctorStatus){
                mEntryAlert.setVisibility(View.VISIBLE);
            }

            mEntryTitle.setText(getResources().getText(mTitle));
            mEntryId.setText(mEntryId.getText() + ": " + (mEntry.entryId + "") );
            mEntryType.setText(mEntryType.getText() + ": "+ mEntry.entryType);
            mEntrySeverity.setText(mEntrySeverity.getText() + ": " +mEntry.painSeverity);
            mEntryCreatedDate.setText(mEntryCreatedDate.getText()  + ": " + new Date(mEntry.entityCreatedDate).toString());
            mEntryUpdatedDate.setText(mEntryUpdatedDate.getText()  + ": " + "");

            if(mClickable){
                view.findViewById(R.id.entry).setOnClickListener(e->{
                    mListener.onEntryInteraction(mEntry);
                });
            }
        }
        return view;
    }

}
