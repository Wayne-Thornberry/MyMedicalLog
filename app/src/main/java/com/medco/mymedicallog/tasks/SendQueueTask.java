package com.medco.mymedicallog.tasks;

import android.content.pm.PackageItemInfo;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Map;

public class SendQueueTask extends AsyncTask<String,Void,Void> {

    private final String mMessageType;
    private final String mQueue;
    private final String mUUID;

    public SendQueueTask(String uuid, String type, String queue) {
        mUUID = uuid;
        mMessageType = type;
        mQueue = queue;
    }

    @Override
    protected Void doInBackground(String... strings) {
       ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("109.78.62.161");
        factory.setCredentialsProvider(new DefaultCredentialsProvider("test","test"));
        String queue = mQueue;
        try   {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queue, false, false, false, null);
            ArrayMap<String, Object> t = new ArrayMap<String,Object>();
            t.put("UUID", mUUID);
            t.put("TYPE", mMessageType);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().headers(t).build();
            channel.basicPublish("", queue,properties , strings[0].getBytes());
            channel.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
