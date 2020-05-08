package com.medco.mymedicallog.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.io.*;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;

public class ReadFromFileTask extends AsyncTask<Void,Void,String> {

    private final WeakReference<Context> mActivity;
    private final String mFileName;

    public ReadFromFileTask(Context activity, String fileName){
        mActivity = new WeakReference<Context>(activity);
        mFileName = fileName;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Context context = mActivity.get();
        String data = "";
        try {
            data = readData(context, mFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String readData(Context context, String fileName) throws FileNotFoundException, IOException{
        FileInputStream fis = context.openFileInput(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while (line != null) {
            stringBuilder.append(line).append('\n');
            line = reader.readLine();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
    }
}
