package com.medco.mymedicallog.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class WriteToFileTask extends AsyncTask<Object,Void,Void> {

    private final WeakReference<Context> mActivity;
    private final String mFileName;

    public WriteToFileTask(Context activity, String fileName){
        mActivity = new WeakReference<Context>(activity);
        mFileName = fileName;
    }

    @Override
    protected Void doInBackground(Object... objects) {
        Context context = mActivity.get();
        Gson gson = new Gson();
        for (Object object : objects){
            String json = gson.toJson(object);
            writeData(context, mFileName, json);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private void writeData(Context context, String fileName, String data) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
