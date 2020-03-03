package com.medco.mymedicallog.ui.portal.selection;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.adapters.PortalListRecyclerViewAdapter;
import com.medco.mymedicallog.database.entities.Profile;
import com.medco.mymedicallog.interfaces.OnPortalListFragmentInteractionListener;

import java.util.List;

public class PortalSelectionFragment extends Fragment implements OnPortalListFragmentInteractionListener {

    private OnPortalListFragmentInteractionListener mListener;
    private RecyclerView mListView;
    private static List<Profile> mProfiles;

    public PortalSelectionFragment() {
    }

    public static PortalSelectionFragment newInstance(List<Profile> profiles) {
        PortalSelectionFragment fragment = new PortalSelectionFragment();
        Bundle args = new Bundle();
        mProfiles = profiles;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portal_selection, container, false);

        mListView = view.findViewById(R.id.profile_list);
        Context context = mListView.getContext();
        mListView.setLayoutManager(new LinearLayoutManager(context));
        mListView.setAdapter(new PortalListRecyclerViewAdapter(mProfiles, mListener));

        if(mProfiles.isEmpty())
            mListView.setVisibility(View.GONE);
        else
            mListView.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPortalListFragmentInteractionListener) {
            mListener = (OnPortalListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListFragmentInteractionListener(Profile item) {
        if(mListener != null)
            mListener.onListFragmentInteractionListener(item);
    }
}
