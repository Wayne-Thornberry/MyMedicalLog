package com.medco.mymedicallog.ui.adapters.holders;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.LogEntry;
import org.w3c.dom.Text;

import java.text.BreakIterator;

public class LogEntryHolder extends RecyclerView.ViewHolder {

    public View viewEntry;
    public LogEntry item;

    public TextView viewId;
    public TextView viewSeverity;
    public TextView viewDate;
    public TextView viewStatus;
    public TextView viewType;

    public LogEntryHolder(View view) {
        super(view);
        viewEntry = view.findViewById(R.id.entry);
        viewId = view.findViewById(R.id.entry_id);
        viewSeverity = view.findViewById(R.id.entry_severity);
        viewDate = view.findViewById(R.id.entry_date);
        viewStatus = view.findViewById(R.id.entry_status);
        viewType = view.findViewById(R.id.entry_type);
    }

    @Override
    public String toString() {
        return super.toString() + " ";
    }
}
