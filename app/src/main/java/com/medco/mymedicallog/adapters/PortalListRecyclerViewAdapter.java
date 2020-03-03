package com.medco.mymedicallog.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.adapters.holders.ProfileHolder;
import com.medco.mymedicallog.database.entities.Profile;
import com.medco.mymedicallog.interfaces.OnPortalListFragmentInteractionListener;

import java.util.List;

public class PortalListRecyclerViewAdapter extends RecyclerView.Adapter<ProfileHolder> {

    private final List<Profile> mValues;
    private final OnPortalListFragmentInteractionListener mListener;

    public PortalListRecyclerViewAdapter(List<Profile> items, OnPortalListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_portal_selection_list_item, parent, false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfileHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).profileId + "");
        holder.mContentView.setText(mValues.get(position).firstName);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues == null) return 0;
        return mValues.size();
    }
}
