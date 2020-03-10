package com.medco.mymedicallog.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.medco.mymedicallog.MyProfile;
import com.medco.mymedicallog.data.UserProfile;
import com.medco.mymedicallog.data.messages.EntryViewedMessage;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.interfaces.OnMessageReceivedListener;
import com.medco.mymedicallog.tasks.UpdateEntryTask;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RabbitMQService extends IntentService implements DeliverCallback {

    private static Context mTest;
    private ConnectionFactory mFactory;
    private Connection mConnection;
    private Channel mChannel;

    public RabbitMQService() {
        super("RabbitMQService");
    }

    // interface to start the service
    public static void startRabbitMQReciving(Context context, UserProfile profile, int id) {
        Intent intent = new Intent(context, RabbitMQService.class);
        intent.putExtra("USER_ID", id);
        intent.putExtra("USER_PROFILE", profile);
        mTest = context;
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int id = intent.getIntExtra("USER_ID", -1);
        UserProfile profile = (UserProfile) intent.getSerializableExtra("USER_PROFILE");
        if(id == -1) return;
        mFactory = new ConnectionFactory();
        mFactory.setHost("64.43.3.207");
        mFactory.setCredentialsProvider(new DefaultCredentialsProvider("test","test"));
        String queue = "USER-ID:" + id + "-QUEUE";
        try {
            mConnection = mFactory.newConnection();
            mChannel = mConnection.createChannel();
            mChannel.queueDeclare(queue, false, false, true, null);
            DeliverCallback deliverCallback = this;
            mChannel.basicConsume(queue, false,  deliverCallback, consumerTag -> {});
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        Log.e("RECIVED", message);
        Gson gson = new Gson();
        try{
            if(delivery.getProperties().getType().equals("DOCTOR_VIEWED_ENTRY")){
                EntryViewedMessage entryViewedMessage = gson.fromJson(message, EntryViewedMessage.class);
                for (LogEntry entry : MyProfile.getInstance().getEntries()){
                    boolean t= entry.entryId == entryViewedMessage.EntryId;
                    Log.e("ENTRY", entry.entryId + " " + t + " " + entryViewedMessage.EntryId);
                    if(entry.entryId == entryViewedMessage.EntryId){
                        entry.viewed = true;
                        new UpdateEntryTask(mTest, 3).execute(entry);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
