package com.medco.mymedicallog.ui.creation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.ui.interfaces.OnCreationInteractionListener;

public class CreationLogFragment extends Fragment {

    private OnCreationInteractionListener mListener;

    public CreationLogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener.onBlockProgress(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creation_log_general, container, false);
        EditText nameText = view.findViewById(R.id.input_name);
        View logAO = view.findViewById(R.id.log_advanced_options);
        CheckBox box = view.findViewById(R.id.log_advanced_options_checkbox);
        box.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                logAO.setVisibility(View.VISIBLE);
            }else{
                logAO.setVisibility(View.INVISIBLE);
            }
        });

        CheckBox box2 = view.findViewById(R.id.log_advanced_options_backup_checkbox);
        box2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mListener.onUploadCheckbox(isChecked);
        });

        CheckBox box3 = view.findViewById(R.id.log_advanced_options_backup_checkbox);
        box3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mListener.onBackupCheckbox(isChecked);
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    mListener.onBlockProgress(true);
                }else{
                    mListener.onBlockProgress(false);
                    mListener.onLogNameEntered(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCreationInteractionListener) {
            mListener = (OnCreationInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCreationInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
