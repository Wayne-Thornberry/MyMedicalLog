package com.medco.mymedicallog;

import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.medco.mymedicallog.interfaces.OnFragmentInteractionListener;
import com.medco.mymedicallog.ui.creation.entry.general.EntryGeneralFragment;
import com.medco.mymedicallog.ui.creation.voice.input.VoiceInputFragment;

import java.util.ArrayList;

public class CreationVoiceActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private final int REQUEST_SPEECH_RECOGNIZER = 3000;
    private int mFragmentDisplaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering);

        VoiceInputFragment voiceInputFragment = VoiceInputFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, voiceInputFragment)
                .commitNow();
        mFragmentDisplaying = 1;
    }

    private void startSpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak your issue");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 900000);
        startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER);
    }

    public void showLog(View view) {
        Intent myIntent = new Intent(this, CreationEntryActivity.class);
        myIntent.putExtra("loadFragment", 1);
        startActivity(myIntent);
    }

    public void startSpeechTranslation(View view) {
        startSpeechRecognizer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SPEECH_RECOGNIZER) {
            TextView textResult = findViewById(R.id.translatedText);
            TextView textProgress = findViewById(R.id.progressText);
            Button contBtn = findViewById(R.id.continueBtn);
            if (resultCode == RESULT_OK) {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(results == null || results.size() == 0) return;
                String result = results.get(0);
                textResult.setText(result);
                contBtn.setVisibility(View.VISIBLE);
            }else{
                textProgress.setText(R.string.recording_translation_progress_text_2);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void enterText(MenuItem item) {
        if(mFragmentDisplaying == 1){
            EntryGeneralFragment medicalDisplayListItemEditFragment = EntryGeneralFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(R.id.container, medicalDisplayListItemEditFragment)
                    .commitNow();
            mFragmentDisplaying = 3;
            item.setTitle(R.string.switchToVoice);
        }else if(mFragmentDisplaying == 3){
            VoiceInputFragment voiceInputFragment = VoiceInputFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(R.id.container, voiceInputFragment)
                    .commitNow();
            mFragmentDisplaying = 1;
            item.setTitle(R.string.switchToText);
        }
    }

    @Override
    public void onFragmentInteractionListener(Uri uri) {

    }
}
