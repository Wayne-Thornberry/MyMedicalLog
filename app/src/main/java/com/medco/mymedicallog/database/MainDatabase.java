package com.medco.mymedicallog.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.medco.mymedicallog.database.dao.LogEntryDao;
import com.medco.mymedicallog.database.dao.ProfileLogDao;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;

@TypeConverters({Converters.class})
@Database(entities = {LogEntry.class, ProfileLog.class}, version = 3)
public abstract class MainDatabase extends RoomDatabase {
    public abstract ProfileLogDao logDao();
    public abstract LogEntryDao entryDao();
    public static MainDatabase _instance;

    public static final int INSERT_LOG_CODE = 101;
    public static final int INSERT_ENTRY_CODE = 102;
    public static final int DELETE_LOG_CODE = 111;
    public static final int DELETE_ENTRY_CODE = 112;
    public static final int UPDATE_ENTRY_CODE = 121;
    public static final int UPDATE_LOG_CODE =  122;
    public static final int SELECT_LOG_CODE = 131;
    public static final int SELECT_ENTRY_CODE = 132;

    public static MainDatabase getInstance(Context context){
        if(_instance == null){
            _instance = Room.databaseBuilder(context, MainDatabase.class, "main-db").build();
        }
        return _instance;
    }
}
