package com.medco.mymedicallog.ui.interfaces;

import com.medco.mymedicallog.database.entities.LogEntry;

import java.util.ArrayList;

public interface OnChangeEntriesInteraction {
    void newEntries(ArrayList<LogEntry> entries);
}
