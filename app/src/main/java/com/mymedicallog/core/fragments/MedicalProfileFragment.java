package com.mymedicallog.core.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mymedicallog.core.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MedicalProfileFragment extends Fragment {

    public MedicalProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medical_profile, container, false);
    }
}
