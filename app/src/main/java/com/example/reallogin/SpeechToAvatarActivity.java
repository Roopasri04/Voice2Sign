package com.example.reallogin;
import com.bumptech.glide.Glide;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

import android.widget.ImageView;

public class SpeechToAvatarActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private ImageView avatarGif;
    private Button startListening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speechtoavatar);
        avatarGif = findViewById(R.id.avatarGif);
        startListening = findViewById(R.id.startListening);

        // Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        startListening.setOnClickListener(view -> startSpeechToAvatar());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) {}
            @Override public void onBeginningOfSpeech() {}
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() {}
            @Override public void onError(int error) {
                Toast.makeText(SpeechToAvatarActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> words = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (words != null && !words.isEmpty()) {
                    String spokenWord = words.get(0).toLowerCase();
                    Toast.makeText(SpeechToAvatarActivity.this, "Heard: " + spokenWord, Toast.LENGTH_SHORT).show();
                    updateAvatar(spokenWord);
                }
            }

            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onEvent(int eventType, Bundle params) {}
        });
    }

    private void startSpeechToAvatar() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.startListening(intent);
    }

    private void updateAvatar(String word) {
        avatarGif.setVisibility(View.VISIBLE);
        int gifResId = -1;

        switch (word) {
            case "hello":
                gifResId = R.drawable.hello;
                break;
            case "no":
                gifResId = R.drawable.no;
                break;
            case "thank you":
                gifResId = R.drawable.thankyou;
                break;
            case "please":
                gifResId = R.drawable.please;
                break;
            case "sorry":
                gifResId = R.drawable.sorry;
                break;
            case "yes":
                gifResId = R.drawable.yes;
                break;
            case "i love you":
                gifResId = R.drawable.iloveyou;
                break;
            case "you":
                gifResId = R.drawable.you;
                break;
            case "my name":
                gifResId = R.drawable.myname;
                break;
            case "i learn sign":
                gifResId = R.drawable.ilearnsign;
                break;
            case "me":
                gifResId = R.drawable.me;
                break;
            case "deaf":
                gifResId = R.drawable.deaf;
                break;
            case "happy":
                gifResId = R.drawable.happy;
                break;
            case "Wonderful":
                gifResId = R.drawable.wonderful;
                break;
            case "appaluse":
                gifResId = R.drawable.appaluse;
                break;
            case "nice to meet you":
                gifResId = R.drawable.nicetomeetyou;
                break;
            case "try again":
                gifResId = R.drawable.tryagain;
                break;
            case "Okay":
                gifResId = R.drawable.okay;
                break;
            case "How are you":
                gifResId = R.drawable.howareyou;
                break;
            case "academic":
                gifResId=R.drawable.academic;
                break;
            case "accumulation":
                gifResId=R.drawable.accumulation;
                break;
            case "across the world":
                gifResId=R.drawable.across_the_world;
                break;
            case "active substance":
                gifResId=R.drawable.active_substance;
                break;
            case "adding multiple times":
                gifResId=R.drawable.adding_multiple_times;
                break;
            case "additionally":
                gifResId=R.drawable.additionally;
                break;
            case "additions":
                gifResId=R.drawable.additions;
                break;
            case "against":
                gifResId=R.drawable.against;
                break;
            case "alcohol":
                gifResId=R.drawable.alcohol;
                break;
            case "alimony":
                gifResId=R.drawable.alimony;
                break;
            case "all around":
                gifResId=R.drawable.all_around;
                break;
            case "arrogant":
                gifResId=R.drawable.arrogant;
                break;
            case "artificial":
                gifResId=R.drawable.artificial;
                break;
            case "blood cell":
                gifResId=R.drawable.blood_cell;
                break;
            case "brain skull":
                gifResId=R.drawable.brain_skull;
                break;
            case "branch":
                gifResId=R.drawable.branch;
                break;
            case "button":
                gifResId=R.drawable.button;
                break;
            case "bubbles":
                gifResId=R.drawable.bubbles;
                break;
            case "basketball player":
                gifResId=R.drawable.basketball_player;
                break;
            case "behave wrongly":
                gifResId=R.drawable.behave_wrongly;
                break;
            case "C":
                gifResId=R.drawable.c;
                break;
            case "carrot":
                gifResId=R.drawable.carrot;
                break;
            case "cells":
                gifResId=R.drawable.cells;
                break;
            case "chips":
                gifResId=R.drawable.chips;
                break;
            case "comic":
                gifResId=R.drawable.comic;
                break;
            case "complex":
                gifResId=R.drawable.complex;
                break;
            case "consistency":
                gifResId=R.drawable.consistently;
                break;
            case "cream":
                gifResId=R.drawable.cream;
                break;
            case "corner":
                gifResId=R.drawable.corner;
                break;



            default:
                Toast.makeText(this, "No sign available for: " + word, Toast.LENGTH_SHORT).show();
                avatarGif.setVisibility(View.INVISIBLE);
                return;
        }

        Glide.with(this)
                .asGif()
                .load(gifResId)
                .into(avatarGif);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null)
            speechRecognizer.destroy();
    }
}
