package com.medco.mymedicallog.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.adapters.holders.LogItemHolder;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnDisplayListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link LogEntry} and makes a call to the
 * specified {@link OnDisplayListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DisplayListRecyclerViewAdapter extends RecyclerView.Adapter<LogItemHolder> {

    private final List<LogEntry> mValues;
    private final OnDisplayListFragmentInteractionListener mListener;

    public DisplayListRecyclerViewAdapter(List<LogEntry> items, OnDisplayListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public LogItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_profile_log_item, parent, false);
        return new LogItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final LogItemHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).entryId + "");
        holder.mContentView.setText(mValues.get(position).fullDescription);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}
