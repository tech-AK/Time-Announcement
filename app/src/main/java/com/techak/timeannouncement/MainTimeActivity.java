package com.techak.timeannouncement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainTimeActivity extends AppCompatActivity {

    TextToSpeech tts;
    TextView view;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_time);

        view = findViewById(R.id.time_textview);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellTime();
            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tellTime();
                }
            }
        });

        tellTime(); //after opening app directly tell time
    }

    private void tellTime() {
        currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        view.setText(currentTime);

        tts.setLanguage(Locale.getDefault());
        tts.speak(currentTime, TextToSpeech.QUEUE_FLUSH, null, "timeTeller");
    }

    @Override
    protected void onPause() {
        super.onPause();

        tts.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();

        currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        view.setText(currentTime);

        //Increase audio volume
        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 80, AudioManager.FLAG_SHOW_UI);

    }
}