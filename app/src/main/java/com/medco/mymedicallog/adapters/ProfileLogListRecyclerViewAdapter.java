package com.medco.mymedicallog.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.adapters.holders.ProfileLogHolder;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.interfaces.OnProfileLogListFragmentInteractionListener;

import java.util.List;

public class ProfileLogListRecyclerViewAdapter extends RecyclerView.Adapter<ProfileLogHolder> {

    private final List<ProfileLog> mValues;
    private final OnProfileLogListFragmentInteractionListener mListener;

    public ProfileLogListRecyclerViewAdapter(List<ProfileLog> items, OnProfileLogListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ProfileLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_log_item, parent, false);
        return new ProfileLogHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ProfileLogHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteractionListener(holder.mItem, position);
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
