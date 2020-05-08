package com.medco.mymedicallog.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.ui.interfaces.OnEntriesInteractionListener;
import com.medco.mymedicallog.ui.adapters.holders.LogEntryHolder;
import com.medco.mymedicallog.database.entities.LogEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LogEntriesAdapter extends RecyclerView.Adapter<LogEntryHolder> {

    private final List<LogEntry> mValues;
    private final OnEntriesInteractionListener mListener;

    public LogEntriesAdapter(List<LogEntry> items, OnEntriesInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public LogEntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_entry_item, parent, false);
        return new LogEntryHolder(view);
    }

    @Override
    public void onBindViewHolder(final LogEntryHolder holder, int position) {
        LogEntry item = mValues.get(position);
        holder.item = mValues.get(position);
        if(holder.item.doctorUploaded){
            holder.viewStatus.setText(R.string.text_status_uploaded);
        }else{
            holder.viewStatus.setText(R.string.text_status_not_uploaded);
        }

        holder.viewId.setText(item.entryId + "");
        holder.viewSeverity.setText(item.painSeverity);
        holder.viewType.setText(item.entryType);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(item.entityCreatedDate);
        holder.viewDate.setText(date);
        holder.viewEntry.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onLogEntryInteraction(holder.item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }

}
