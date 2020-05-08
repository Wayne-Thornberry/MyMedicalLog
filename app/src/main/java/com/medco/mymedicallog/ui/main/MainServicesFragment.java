package com.medco.mymedicallog.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.data.ServiceData;
import com.medco.mymedicallog.ui.adapters.ServicesAdapter;

public class MainServicesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_services, container, false);
        GridView gridView = view.findViewById(R.id.gridview);
        ServicesAdapter t = new ServicesAdapter(this.getContext(), new ServiceData[]{new ServiceData(), new ServiceData()});
        gridView.setAdapter(t);
        return view;
    }
}
