package com.medco.mymedicallog.ui.interfaces;

import com.medco.mymedicallog.database.entities.ProfileLog;

public interface OnLogsInteractionListener {
    void onLogsLogInteraction(ProfileLog item);
    void onNewLogInteraction();
}
