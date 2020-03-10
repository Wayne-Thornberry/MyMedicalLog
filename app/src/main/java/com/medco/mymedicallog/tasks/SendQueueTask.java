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
    private final int mDoctorId;

    public SendQueueTask(String type, int id) {
        mMessageType = type;
        mDoctorId = id;
    }

    @Override
    protected Void doInBackground(String... strings) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("64.43.3.207");
        factory.setCredentialsProvider(new DefaultCredentialsProvider("test","test"));
        String queue = "DOCTOR-ID:" + mDoctorId + "-QUEUE";
        try   {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queue, false, false, false, null);
            ArrayMap<String, Object> t = new ArrayMap<String,Object>();
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
