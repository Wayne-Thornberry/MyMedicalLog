package com.medco.mymedicallog.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.adapters.holders.LogEntryHolder;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnLogEntryListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link LogEntry} and makes a call to the
 * specified {@link OnLogEntryListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LogEntryListRecyclerViewAdapter extends RecyclerView.Adapter<LogEntryHolder> {

    private final List<LogEntry> mValues;
    private final OnLogEntryListFragmentInteractionListener mListener;

    public LogEntryListRecyclerViewAdapter(List<LogEntry> items, OnLogEntryListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public LogEntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_profile_entry_item, parent, false);
        return new LogEntryHolder(view);
    }

    @Override
    public void onBindViewHolder(final LogEntryHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).entryId + "");
        holder.mContentView.setText(mValues.get(position).fullDescription);

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
        return mValues.size();
    }

}
