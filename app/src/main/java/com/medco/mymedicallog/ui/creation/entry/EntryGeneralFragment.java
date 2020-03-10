package com.medco.mymedicallog.ui.creation.entry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;


public class EntryGeneralFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public EntryGeneralFragment() {
        // Required empty public constructor
    }

    public static EntryGeneralFragment newInstance() {
        EntryGeneralFragment fragment = new EntryGeneralFragment();
        Bundle args = new Bundle();
        ArrayList<String> t = new ArrayList<>();
        t.add("Paracetamol");
        args.putString("ME_RDATE", "03/12/1996");
        args.putString("ME_ESTDATE", "00/00/0000");
        args.putString("ME_PDURATION", "3 Days");
        args.putInt("ME_PSEVERITY",   8);
        args.putString("ME_PTYPE",   "Sharp");
        args.putString("ME_PLOC",    "Temples, Head");
        args.putString("ME_PEXP",    "Disabling");
        args.putString("ME_FDESC",    "I have a pain in my head located near the temples, its a sharp pain that is pretty disabling and is severity is about a 8");
        args.putStringArrayList("ME_MUSED", t);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //String[] p8 = savedInstanceState.getStringArrayList("ME_MUSED").toArray(p8);

        //MedicalEntry me = new MedicalEntry(new Date("03/12/1996"), new Date("00/00/0000"), "3 Days", 8, "Sharp", "Temples, Head", "Disabling", "I have a pain in my head located near the temples, its a sharp pain that is pretty disabling and\n" +
          //      "is severity is about a 8", new String[]{"Paracetamol"});

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creation_entry_general, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*EditText et1 = view.findViewById(R.id.recordDateET);
        EditText et2 = view.findViewById(R.id.estStartDateET);
        EditText et3 = view.findViewById(R.id.durationET);
        EditText et4 = view.findViewById(R.id.severityET);
        EditText et5 = view.findViewById(R.id.typeET);
        EditText et6 = view.findViewById(R.id.locationET);
        EditText et7 = view.findViewById(R.id.experienceET);
        EditText et8 = view.findViewById(R.id.fullDescET);
        EditText et9 = view.findViewById(R.id.medicationUsedET);

        String p0 = getArguments().getString("ME_RDATE");
        String p1 = getArguments().getString("ME_ESTDATE");
        String p2 = getArguments().getString("ME_PDURATION");
        int p3 = getArguments().getInt("ME_PSEVERITY");
        String p4 = getArguments().getString("ME_PTYPE");
        String p5 = getArguments().getString("ME_PLOC");
        String p6 = getArguments().getString("ME_PEXP");
        String p7 = getArguments().getString("ME_FDESC");

        et1.setText(p0);
        et2.setText(p1);
        et3.setText(p2);
        et4.setText(p3 + "");
        et5.setText(p4);
        et6.setText(p5);
        et7.setText(p6);
        et8.setText(p7);
        et9.setText("Paracetamol");*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
