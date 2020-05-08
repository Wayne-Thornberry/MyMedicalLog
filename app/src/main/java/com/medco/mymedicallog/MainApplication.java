package com.medco.mymedicallog;

import android.app.Application;
import android.util.Log;
import com.google.gson.Gson;
import com.medco.mymedicallog.data.GeneralData;
import com.medco.mymedicallog.data.messages.*;
import com.medco.mymedicallog.database.entities.LogEntry;
import com.medco.mymedicallog.database.entities.ProfileLog;
import com.medco.mymedicallog.tasks.DatabaseTask;
import com.medco.mymedicallog.tasks.ReadFromFileTask;
import com.medco.mymedicallog.tasks.WriteToFileTask;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.medco.mymedicallog.database.MainDatabase.*;

public class MainApplication extends Application implements DeliverCallback {

    private Thread mThread;

    @Override
    public void onCreate() {
        super.onCreate();
        GeneralData generalData = loadGeneralData();
        if(generalData == null) return;
        String queue = "USER-ID:" + generalData.uuid + "-QUEUE";
        mThread = new Thread(() -> {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("109.78.62.161");
            factory.setCredentialsProvider(new DefaultCredentialsProvider("test", "test"));
            try {
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                channel.queueDeclare(queue, false, false, true, null);
                DeliverCallback deliverCallback = this;
                channel.basicConsume(queue, false,  deliverCallback, consumerTag -> {});
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        mThread.start();
    }

    private GeneralData loadGeneralData() {
        try {
            String data = "";
            Gson gson = new Gson();
            while(data.equals("")){
                data = new ReadFromFileTask(this, "GeneralData").execute().get();
                if (data == null || data.equals("")) {
                    GeneralData generalData = new GeneralData();
                    Log.e("Run", "Main");
                    generalData.uuid = UUID.randomUUID().toString();
                    new WriteToFileTask(this, "GeneralData").execute(generalData).get();
                }else {
                    return gson.fromJson(data, GeneralData.class);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mThread.interrupt();
    }

    @Override
    public void handle(String tag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        String type = delivery.getProperties().getType();
        try{
            Gson gson = new Gson();
            if(type.equals("DOCTOR_STATUS")){
                GeneralData generalData = loadGeneralData();
                if(generalData == null) return;
                DoctorStatusMessage message1 = gson.fromJson(message, DoctorStatusMessage.class);
                generalData.doctorStatus = message1.status;
                generalData.doctorLastOnline = message1.lastonline;
                new WriteToFileTask(this, "GeneralData").execute(generalData).get();
            }else if(type.equals("LOG_AND_ENTRIES")){
                Log.e("Messages", "CAme Throuhg");
                ProfileLogAndEntriesMessage logAndEntriesMessage = gson.fromJson(message, ProfileLogAndEntriesMessage.class);
                if(logAndEntriesMessage == null) return;
                ProfileLog log = (ProfileLog) new DatabaseTask(this, SELECT_LOG_CODE).execute(logAndEntriesMessage.profileLogMessage.logName).get();
                if(log == null) return;
                Log.e("Messages", "Foujnd log Throuhg");
                List<LogEntry> entries = (List<LogEntry>) new DatabaseTask(this, SELECT_ENTRY_CODE).execute(log.logId).get();
                if(entries == null) return;
                Log.e("Messages", "Foujnd entries Throuhg");
                for (LogEntryMessage logEntryMessage : logAndEntriesMessage.logEntryMessages){
                    for (LogEntry entry : entries){
                        if(logEntryMessage.entryId == entry.entryId){
                            entry.doctorStatus = true;
                            Log.e("Messages", "Setting status");
                            new DatabaseTask(this, UPDATE_ENTRY_CODE).execute(entry).get();
                        }
                    }
                }
            }else if(type.equals("USER_CONNECTION_REQUEST") || type.equals("DOCTOR_DETAILS") || type.equals("PATIENT_DETAILS")){
                GeneralData generalData = loadGeneralData();
                if(generalData == null) return;
                ConnectionRequestMessage connectionRequestMessage = gson.fromJson(message, ConnectionRequestMessage.class);
                switch (type) {
                    case "USER_CONNECTION_REQUEST":
                        generalData.isDoctorRequestPending = false;
                        if (connectionRequestMessage.approved) {
                            generalData.isDoctorConnected = true;
                        } else {
                            generalData.rejectReason = connectionRequestMessage.reason;
                        }
                        break;
                    case "DOCTOR_DETAILS":
                        DoctorDetailsMessage doctorDetailsMessage = gson.fromJson(message, DoctorDetailsMessage.class);
                        generalData.doctorId = doctorDetailsMessage.id;
                        generalData.doctorName = doctorDetailsMessage.name;
                        break;
                    case "PATIENT_DETAILS":
                        PatientDetailsMessage patientDetailsMessage = gson.fromJson(message, PatientDetailsMessage.class);
                        generalData.patientPPSNo = patientDetailsMessage.ppsno;
                        generalData.patientName = patientDetailsMessage.name;
                        generalData.patientAge = patientDetailsMessage.age;
                        generalData.patientGender = patientDetailsMessage.gender;
                        break;

                }
                new WriteToFileTask(this, "GeneralData").execute(generalData).get();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
