package com.medco.mymedicallog.ui.creation;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreationHomeFragment extends Fragment {


    public CreationHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creation_home, container, false);
    }

}
