package com.medco.mymedicallog.ui.interfaces;

import com.medco.mymedicallog.database.entities.LogEntry;

public interface OnEntriesInteractionListener {
    void onLogEntryInteraction(LogEntry entry);
}
