package com.medco.mymedicallog.ui.interfaces;

import com.medco.mymedicallog.database.entities.LogEntry;

public interface OnEntryInteractionListener {
    void onEntryInteraction(LogEntry entry);
    void onEntryErrorInteraction();
}
