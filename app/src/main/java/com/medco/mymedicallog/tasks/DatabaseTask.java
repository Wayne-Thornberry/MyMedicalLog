package com.medco.mymedicallog.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.medco.mymedicallog.database.MainDatabase;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.ui.interfaces.OnTaskFinishedListener;

import java.lang.ref.WeakReference;

import static com.medco.mymedicallog.database.MainDatabase.*;

public class DatabaseTask extends AsyncTask<Object, Void, Object> {

    private final WeakReference<Context> mActivity;
    private final int mCode;

    public DatabaseTask(Context activity, int code){
        mActivity = new WeakReference<Context>(activity);
        mCode = code;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        Context context = mActivity.get();
        if(mCode == SELECT_LOG_CODE || mCode == SELECT_ENTRY_CODE){
            switch (mCode){
                case SELECT_LOG_CODE:
                    if(objects.length == 1) {
                        return  MainDatabase.getInstance(context).logDao().select((String) objects[0]);
                    }
                    return  MainDatabase.getInstance(context).logDao().select();
                case SELECT_ENTRY_CODE:
                    if(objects.length == 1){
                        return MainDatabase.getInstance(context).entryDao().select((long) objects[0]);
                    }
                    return MainDatabase.getInstance(context).entryDao().select();
            }
        }else{
            for (Object object: objects) {
                switch (mCode){
                    case INSERT_LOG_CODE: MainDatabase.getInstance(context).logDao().insert((ProfileLog) object); break;
                    case INSERT_ENTRY_CODE: MainDatabase.getInstance(context).entryDao().insert((LogEntry) object); break;
                    case DELETE_LOG_CODE: MainDatabase.getInstance(context).logDao().delete((ProfileLog) object); break;
                    case DELETE_ENTRY_CODE: MainDatabase.getInstance(context).entryDao().delete((LogEntry) object); break;
                    case UPDATE_LOG_CODE: MainDatabase.getInstance(context).logDao().update((ProfileLog) object); break;
                    case UPDATE_ENTRY_CODE : MainDatabase.getInstance(context).entryDao().update((LogEntry) object); break;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object aVoid) {
        super.onPostExecute(aVoid);
    }
}
