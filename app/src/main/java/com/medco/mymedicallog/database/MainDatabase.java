package com.medco.mymedicallog.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.medco.mymedicallog.database.dao.LogEntryDao;
import com.medco.mymedicallog.database.dao.ProfileLogDao;
import com.medco.mymedicallog.database.dao.ProfileDao;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.database.entities.Profile;

@TypeConverters({Converters.class})
@Database(entities = {Profile.class, LogEntry.class, ProfileLog.class}, version = 2)
public abstract class MainDatabase extends RoomDatabase {
    public abstract ProfileDao profileDao();
    public abstract ProfileLogDao logDao();
    public abstract LogEntryDao entryDao();
    public static MainDatabase _instance;

    public static MainDatabase getInstance(Context context){
        if(_instance == null){
            _instance = Room.databaseBuilder(context, MainDatabase.class, "main-db").build();
        }
        return _instance;
    }
}
