package com.medco.mymedicallog.ui.main.summery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.ui.main.profile.MainProfileFragment;

public class MainSummeryFragment extends Fragment {

    public static MainProfileFragment newInstance() {
        MainProfileFragment fragment = new MainProfileFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summery, container, false);
    }
}
