package com.medco.mymedicallog.ui.main.profile;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.medco.mymedicallog.R;

public class MainProfileFragment extends Fragment {

    public MainProfileFragment() {
    }

    public static MainProfileFragment newInstance() {
        MainProfileFragment fragment = new MainProfileFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_profile, container, false);
    }
}
