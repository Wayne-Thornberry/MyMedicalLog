package com.medco.mymedicallog.ui.interfaces;

import android.view.View;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;

public interface OnLogInteractionListener {
    void onLogInteraction(ProfileLog log);
    void onLogErrorInteraction();
}
