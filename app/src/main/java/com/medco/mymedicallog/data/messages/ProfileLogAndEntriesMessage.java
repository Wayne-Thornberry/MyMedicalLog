package com.medco.mymedicallog.data.messages;

import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;

import java.util.List;

public class ProfileLogAndEntriesMessage {
    public ProfileLogMessage profileLogMessage;
    public List<LogEntryMessage> logEntryMessages;
}
