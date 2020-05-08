package com.medco.mymedicallog.ui.interfaces;

import android.view.View;
import com.medco.mymedicallog.database.entities.LogEntry;

import java.util.List;

public interface OnDoctorInteractionListener {
    void onUploadEntriesInteraction(List<LogEntry> mEntries);
    void onDoctorInteraction();
    void onDoctorErrorInteraction();
    void onDoctorSyncInteraction();
}
