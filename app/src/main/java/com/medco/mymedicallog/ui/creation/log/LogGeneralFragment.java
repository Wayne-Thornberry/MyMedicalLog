package com.medco.mymedicallog.ui.creation.log;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

public class LogGeneralFragment extends Fragment implements OnFragmentInteractionListener {
    private OnFragmentInteractionListener mListener;

    public LogGeneralFragment() {
        // Required empty public constructor
    }

    public static LogGeneralFragment newInstance() {
        LogGeneralFragment fragment = new LogGeneralFragment();
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
        View view = inflater.inflate(R.layout.fragment_creation_log_general, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onProfileCreateBtnPressedd(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onFragmentInteraction(Uri uri) {

    }
}
