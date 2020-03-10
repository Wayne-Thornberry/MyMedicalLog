package com.medco.mymedicallog.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;

import java.nio.charset.StandardCharsets;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RabbitMQService extends IntentService {

    private ConnectionFactory mFactory;
    private Connection mConnection;
    private Channel mChannel;

    public RabbitMQService() {
        super("RabbitMQService");
    }

    // interface to start the service
    public static void startRabbitMQReciving(Context context, int id) {
        Intent intent = new Intent(context, RabbitMQService.class);
        intent.putExtra("USER_ID", id);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("SERVICE","WOKRING");
        int id = intent.getIntExtra("USER_ID", -1);
        if(id == -1) return;
        mFactory = new ConnectionFactory();
        mFactory.setHost("64.43.3.207");
        mFactory.setCredentialsProvider(new DefaultCredentialsProvider("test","test"));
        String queue = "USER-ID:" + id + "-QUEUE";
        try {
            mConnection = mFactory.newConnection();
            mChannel = mConnection.createChannel();
            mChannel.queueDeclare(queue, false, false, true, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                Log.e("RECIVED",message );
            };
            mChannel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
