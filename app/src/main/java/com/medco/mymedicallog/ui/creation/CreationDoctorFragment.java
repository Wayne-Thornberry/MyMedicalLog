package com.medco.mymedicallog.ui.creation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.entities.DoctorCreation;
import com.medco.mymedicallog.ui.interfaces.OnCreationInteractionListener;

public class CreationDoctorFragment extends Fragment implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    private OnCreationInteractionListener mListener;
    private TextView mPPSNo;
    private TextView mDoctorCode;
    private TextView mPassCode;
    private DoctorCreation mDoctor;
    private CheckBox mTickBox;

    public CreationDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null){
            mDoctor = (DoctorCreation) this.getArguments().getSerializable("CREATION_DOCTOR_DATA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_creation_doctor, container, false);
        mPPSNo = view.findViewById(R.id.input_ppsno);
        mDoctorCode = view.findViewById(R.id.input_doctor_code);
        mPassCode = view.findViewById(R.id.input_doctor_pass);

        mPPSNo.setText(mDoctor.ppsno);
        mTickBox = view.findViewById(R.id.tick_doctor);
        mTickBox.setOnCheckedChangeListener(this);

        mPPSNo.addTextChangedListener(this);
        mDoctorCode.addTextChangedListener(this);
        mPassCode.addTextChangedListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener.onBlockProgress(true);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateStuff();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updateStuff();
    }

    private void updateStuff() {
        if(mPPSNo.getText() == null || mDoctorCode.getText() == null  || mPassCode.getText() == null ) return;

        String ppsno = mPPSNo.getText().toString();
        String doctorCode = mDoctorCode.getText().toString();
        String passCode = mPassCode.getText().toString();

        if(ppsno.equals("") || doctorCode.equals("") || passCode.equals("")) return;

        if(!mTickBox.isChecked()) return;
        mListener.onBlockProgress(false);
        int dc = Integer.parseInt(doctorCode);
        int pc = Integer.parseInt(passCode);
        mListener.onDoctorDetails(ppsno, dc, pc);
    }
}
