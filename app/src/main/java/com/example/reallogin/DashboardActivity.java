package com.example.reallogin;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViewById(R.id.cardVideoCall).setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, JitsiVideoCallActivity.class));
        });
        CardView cardLearning = findViewById(R.id.cardLearning);
        cardLearning.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LearningActivity.class);
            startActivity(intent);
        });
        CardView translationCard = findViewById(R.id.cardChatTranslate);
        translationCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, TranslationActivity.class);
            startActivity(intent);
        });
        CardView cardSOS = findViewById(R.id.cardSOS);
        cardSOS.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, EmergencyActivity.class);
            startActivity(intent);
        });
        CardView cardSpeechToAvatar = findViewById(R.id.cardSpeechToAvatar);
        cardSpeechToAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SpeechToAvatarActivity.class);
            startActivity(intent);
        });
        CardView cardGame = findViewById(R.id.cardGame);

        cardGame.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, Game.class);
            startActivity(intent);
        });


    }
}