package com.medco.mymedicallog.ui.interfaces;


import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.entities.SelectorItem;

public interface OnCreationInteractionListener {
    void onBlockProgress(boolean blockProgress);
    void onLogNameEntered(String toString);

    void onUploadCheckbox(boolean isChecked);
    void onBackupCheckbox(boolean isChecked);

    void onLocationSelected(String location);
    void onLocationDeSelected(String location);

    void onGeneralDetails(String username, String password, String email);
    void onDoctorDetails(String ppsno, int doctorCode, int passCode);

    void onEntityChanged(LogEntry mEntry);
}