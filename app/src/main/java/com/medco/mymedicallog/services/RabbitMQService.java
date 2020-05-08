package com.medco.mymedicallog.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.medco.mymedicallog.data.GeneralData;
import com.medco.mymedicallog.data.messages.ConnectionRequestMessage;
import com.medco.mymedicallog.data.messages.DoctorDetailsMessage;
import com.medco.mymedicallog.data.messages.PatientDetailsMessage;
import com.medco.mymedicallog.tasks.ReadFromFileTask;
import com.medco.mymedicallog.tasks.WriteToFileTask;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RabbitMQService extends Service {

    private Thread mThread;
    private GeneralData mProfile;

    public RabbitMQService() {
        super();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mThread != null)
            mThread.interrupt();
        mThread = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void SaveProfile() {
        new WriteToFileTask(this, "ProfileData").execute(mProfile);
    }
}


//EntryViewedMessage entryViewedMessage = gson.fromJson(message, EntryViewedMessage.class);
//List<LogEntry> entries = (List<LogEntry>) new DatabaseTask(this, MainDatabase.SELECT_LOG_CODE).execute().get();
//for (LogEntry entry : entries){
//    boolean t= entry.entryId == entryViewedMessage.EntryId;
//    Log.e("ENTRY", entry.entryId + " " + t + " " + entryViewedMessage.EntryId);
//    if(entry.entryId == entryViewedMessage.EntryId){
//        entry.doctorViewed = true;
//        break;
//    }
//}
//new DatabaseTask(this, MainDatabase.UPDATE_LOG_CODE).execute(entries).get();
