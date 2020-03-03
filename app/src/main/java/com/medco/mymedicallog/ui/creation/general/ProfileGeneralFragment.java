package com.medco.mymedicallog.ui.creation.general;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

public class ProfileGeneralFragment extends Fragment implements OnFragmentInteractionListener {
    private OnFragmentInteractionListener mListener;

    public ProfileGeneralFragment() {
        // Required empty public constructor
    }

    public static ProfileGeneralFragment newInstance() {
        ProfileGeneralFragment fragment = new ProfileGeneralFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creation_general, container, false);
        CheckBox checkbox = view.findViewById(R.id.check_enable_security);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout mLinearLayout = buttonView.getRootView().findViewById(R.id.linear_security);
                if(isChecked){
                    mLinearLayout.setVisibility(View.VISIBLE);
                }else{
                    mLinearLayout.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onProfileCreateBtnPressedd(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public void onFragmentInteractionListener(Uri uri) {

    }
}
