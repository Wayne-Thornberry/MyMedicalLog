package com.medco.mymedicallog.ui.creation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.entities.AccountCreation;
import com.medco.mymedicallog.ui.interfaces.OnCreationInteractionListener;

public class CreationAccountFragment extends Fragment implements TextWatcher {

    private OnCreationInteractionListener mListener;
    private AccountCreation mAccountCreation;

    private EditText mUsername;
    private EditText mPassword;
    private EditText mEmailAddress;


    public CreationAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener.onBlockProgress(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null){
            mAccountCreation = (AccountCreation) this.getArguments().getSerializable("CREATION_USER_DATA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_creation_profile_general, container, false);
        mUsername = view.findViewById(R.id.input_username);
        mPassword = view.findViewById(R.id.input_password);
        mEmailAddress = view.findViewById(R.id.input_emailaddress);

        mUsername.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);
        mEmailAddress.addTextChangedListener(this);

        return view;
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
        if(mUsername.getText().toString().equals("") || mPassword.getText().toString().equals("") || mEmailAddress.getText().toString().equals("")){
            mListener.onBlockProgress(true);
        }else{
            mListener.onBlockProgress(false);
        }
        mListener.onGeneralDetails(mUsername.getText().toString(), mPassword.getText().toString(), mEmailAddress.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
