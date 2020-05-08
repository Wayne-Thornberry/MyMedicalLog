package com.medco.mymedicallog.ui.adapters.holders;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.ProfileLog;

import java.text.BreakIterator;

public class ProfileLogHolder extends RecyclerView.ViewHolder {

    public ProfileLog item;
    public View viewLog;
    public TextView viewName;
    public TextView viewDate;
    public TextView viewStatus;
    public TextView viewId;

    public ProfileLogHolder(View view) {
        super(view);
        viewLog = view.findViewById(R.id.log);
        viewId = view.findViewById(R.id.log_id);
        viewName = view.findViewById(R.id.log_name);
        viewDate = view.findViewById(R.id.log_date);
        viewStatus = view.findViewById(R.id.log_status);
    }
}