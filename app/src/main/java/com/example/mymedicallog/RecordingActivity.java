package com.example.mymedicallog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

public class RecordingActivity extends AppCompatActivity {
    private String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
    private MediaRecorder myAudioRecorder;
    private boolean isRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setAudioEncodingBitRate(16 * 44100);
        myAudioRecorder.setAudioSamplingRate(44100);
        myAudioRecorder.setOutputFile(outputFile);
    }



    public void showProfile(View view) {
        Intent myIntent = new Intent(this, ProfileActivity.class);
        startActivity(myIntent);
    }

    public void showLog(View view) {
        Intent myIntent = new Intent(this, LogActivity.class);
        startActivity(myIntent);
    }

    public void controlRecording(View view) {
        if(myAudioRecorder == null) return;
        if(!isRecording){
            Log.e("Recording", "Started");
            try {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
            } catch (IllegalStateException ise) {
                // make something ...
            } catch (IOException e) {
                e.printStackTrace();
            }
            isRecording = true;
        }else{
            try {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                Log.e("Recording", "Stopped");

                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(outputFile);
                mediaPlayer.prepare();
                mediaPlayer.start();
                Log.e("Recording", "Playing");
            }catch (IllegalStateException e){
              e.printStackTrace();
            }
             catch (Exception e) {
                // make something
            }

            isRecording = false;
        }
    }
}
