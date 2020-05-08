package com.medco.mymedicallog.ui.creation.entry;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.entities.SelectorItem;
import com.medco.mymedicallog.ui.adapters.SelectorAdapter;
import com.medco.mymedicallog.ui.interfaces.OnAvatarInteractionListener;
import com.medco.mymedicallog.ui.interfaces.OnCreationInteractionListener;
import com.medco.mymedicallog.ui.interfaces.OnSelectorInteraction;
import com.medco.mymedicallog.ui.views.AvatarView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class EntryTouchInputFragment extends Fragment implements View.OnClickListener, OnAvatarInteractionListener, OnSelectorInteraction, SeekBar.OnSeekBarChangeListener, TextWatcher {

    private OnCreationInteractionListener mListener;
    private LogEntry mEntry;
    private SeekBar mBar;
    private AvatarView mAvatarView;
    private RecyclerView mRecyclerView;
    private int mColumnCount = 3;
    private ArrayList<String> mSelectedItems;
    private ArrayList<SelectorItem> mItems;

    private Button mSeverityMild;
    private Button mSeverityModerate;
    private Button mSeveritySevere;

    private Button mNoticablity0;
    private Button mNoticablity1;
    private Button mNoticablity2;
    private Button mNoticablity3;
    private Button mNoticablity4;


    private Button mSelectedSeverity;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView mPainStrength;
    private EditText mAddionalsText;
    private Button mSelectedNoticablity;


    public EntryTouchInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mEntry = (LogEntry) this.getArguments().getSerializable("CREATION_ENTRY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creation_entry_touch_input, container, false);

        btnDatePicker=(Button)view.findViewById(R.id.btn_date);
        btnTimePicker=(Button)view.findViewById(R.id.btn_time);
        txtDate=(EditText)view.findViewById(R.id.in_date);
        txtTime=(EditText)view.findViewById(R.id.in_time);

        mSeverityMild = view.findViewById(R.id.severity_mild);
        mSeverityModerate = view.findViewById(R.id.severity_moderate);
        mSeveritySevere = view.findViewById(R.id.severity_severe);

        mNoticablity0 = view.findViewById(R.id.notability_0);
        mNoticablity1 = view.findViewById(R.id.notability_1);
        mNoticablity2 = view.findViewById(R.id.notability_2);
        mNoticablity3 = view.findViewById(R.id.notability_3);
        mNoticablity4 = view.findViewById(R.id.notability_4);

        mNoticablity0.setOnClickListener(this);
        mNoticablity1.setOnClickListener(this);
        mNoticablity2.setOnClickListener(this);
        mNoticablity3.setOnClickListener(this);
        mNoticablity4.setOnClickListener(this);

        if(mEntry.painDescription == null){
            mEntry.painDescription = "";
        }

        mSeverityMild.setOnClickListener(this);
        mSeverityModerate.setOnClickListener(this);
        mSeveritySevere.setOnClickListener(this);

        mAddionalsText = view.findViewById(R.id.pain_additional);
        mAddionalsText.addTextChangedListener(this);
        mAddionalsText.setText(mEntry.painDescription);

        mBar = view.findViewById(R.id.pain_strength);
        mPainStrength = view.findViewById(R.id.pain_strength_text);
        mBar.setOnSeekBarChangeListener(this);

        ArrayList<String> painLocs = new ArrayList<>();
        painLocs.addAll(Arrays.asList(mEntry.painLocation.split(" ")));
        mAvatarView = view.findViewById(R.id.avatar_view);
        mAvatarView.setSelectedTags(painLocs);
        mAvatarView.setOnAvatarInteraction(this);
        mAvatarView.setView(0);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);



        if(mEntry.painStartDate > 0) {
            Date date = new Date(mEntry.painStartDate);
            txtDate.setText(date.getDay() + "-" + (date.getMonth() + 1) + "-" + date.getYear());
            txtTime.setText(date.getHours() + ":" + date.getMinutes());
        }else{
            Date date = new Date();
            txtDate.setText(date.getDay() + "-" + (date.getMonth() + 1) + "-" + date.getYear());
            txtTime.setText(date.getHours() + ":" + date.getMinutes());
        }

        if(mEntry.painNotice == null)
            mEntry.painNotice = "";

        switch (mEntry.painNotice){
            case "Tiny" :
                mSelectedNoticablity = mNoticablity0;
                break;
            case "Small" :
                mSelectedNoticablity = mNoticablity1;
                break;
            case "Somewhat" :
                mSelectedNoticablity = mNoticablity2;
                break;
            case "Very" :
                mSelectedNoticablity = mNoticablity3;
                break;
            case "Extremely" :
                mSelectedNoticablity = mNoticablity4;
                break;
        }


        if(mSelectedNoticablity != null){
            mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }

        if(mEntry.painSeverity != null){
            switch (mEntry.painSeverity){
                case "Mild" :
                    mSelectedSeverity = mSeverityMild;
                    break;
                case "Moderate" :
                    mSelectedSeverity = mSeverityModerate;break;
                case "Severe" :
                    mSelectedSeverity = mSeveritySevere; break;
            }
        }
        if(mSelectedSeverity != null)
            mSelectedSeverity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));

        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.entry_list);
        mRecyclerView.setNestedScrollingEnabled(false);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        if(mEntry.painType == null){
            mEntry.painType = "";
        }
        mSelectedItems = new ArrayList<>();
        mSelectedItems.addAll(Arrays.asList(mEntry.painType.split(" ")));

        mItems = new ArrayList<>();
        mItems.add(new SelectorItem("SHARP"));
        mItems.add(new SelectorItem("PULSATING"));
        mItems.add(new SelectorItem("DULL"));
        mItems.add(new SelectorItem("POUNDING"));
        mItems.add(new SelectorItem("THROBBING"));

        for (SelectorItem selectorItem : mItems){
            if(mSelectedItems.contains(selectorItem.title)){
                selectorItem.selected = true;
            }
        }
        mBar.setProgress(mEntry.painStrength);

        mRecyclerView.setAdapter(new SelectorAdapter(mItems, this));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCreationInteractionListener) {
            mListener = (OnCreationInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {


        if(v == mNoticablity0){
            if(mSelectedNoticablity != null){
                mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painNotice = "Tiny";
            mSelectedNoticablity = mNoticablity0;
            mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }
        else if(v ==mNoticablity1){
            if(mSelectedNoticablity != null){
                mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painNotice = "Small";
            mSelectedNoticablity = mNoticablity1;
            mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }
        else if(v == mNoticablity2){
            if(mSelectedNoticablity != null){
                mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painNotice = "Somewhat";
            mSelectedNoticablity = mNoticablity2;
            mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }
        else if(v == mNoticablity3){
            if(mSelectedNoticablity != null){
                mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painNotice = "Very";
            mSelectedNoticablity = mNoticablity3;
            mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }
        else if(v == mNoticablity4){
            if(mSelectedNoticablity != null){
                mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painNotice = "Extremely";
            mSelectedNoticablity = mNoticablity4;
            mSelectedNoticablity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }

        if(v == mSeverityMild){
            if(mSelectedSeverity != null){
                mSelectedSeverity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painSeverity = "Mild";
            mSelectedSeverity = mSeverityMild;
            mSelectedSeverity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        } else if(v == mSeverityModerate){
            if(mSelectedSeverity != null){
                mSelectedSeverity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painSeverity = "Moderate";
            mSelectedSeverity = mSeverityModerate;
            mSelectedSeverity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        } else if(v == mSeveritySevere){
            if(mSelectedSeverity != null){
                mSelectedSeverity.setBackgroundColor(getResources().getColor(R.color.color_button_unselected));
            }
            mEntry.painSeverity = "Severe";
            mSelectedSeverity = mSeveritySevere;
            mSelectedSeverity.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }


        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                    (view, year, monthOfYear, dayOfMonth) -> {
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        mEntry.painStartDate = new Date(year, monthOfYear, dayOfMonth).getTime();
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                    (view, hourOfDay, minute) -> {
                        txtTime.setText(hourOfDay + ":" + minute);
                        long time = new Time(hourOfDay, minute, 0).getTime();
                        Date date = new Date(mEntry.painStartDate);
                        date.setTime(time);
                        mEntry.painStartDate = date.getTime();
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        mListener.onEntityChanged(mEntry);
    }


    @Override
    public void onAvatarCellInteraction(String tag, boolean selected) {
        if(selected)
            mListener.onLocationSelected(tag);
        else
            mListener.onLocationDeSelected(tag);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(View view, SelectorItem item) {
        if(mEntry.painType == null){
            mEntry.painType = "";
        }
        if(mEntry.painType.contains(item.title)){
            mEntry.painType = mEntry.painType.replace(item.title, "");
            view.setBackgroundColor(0);
        }else{
            mEntry.painType += item.title + " ";
            view.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
        }
        mListener.onEntityChanged(mEntry);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            mEntry.painStrength = progress;
            mListener.onEntityChanged(mEntry);
        }
        mPainStrength.setText(progress + "");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mEntry.painDescription = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
