package com.medco.mymedicallog.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.ui.adapters.holders.ProfileLogHolder;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.ui.interfaces.OnLogsInteractionListener;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProfileLogsAdapter extends RecyclerView.Adapter<ProfileLogHolder> {

    private final List<ProfileLog> mValues;
    private final OnLogsInteractionListener mListener;

    public ProfileLogsAdapter(List<ProfileLog> items, OnLogsInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ProfileLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_log_item, parent, false);
        return new ProfileLogHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ProfileLogHolder holder, final int position) {
        holder.item = mValues.get(position);
        holder.viewId.setText(holder.item.logId + "");
        holder.viewName.setText(holder.item.logName);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(holder.item.logUpdatedDate);
        holder.viewDate.setText(date);
        holder.viewLog.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onLogsLogInteraction(holder.item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues == null) return 0;
        return mValues.size();
    }
}
