package com.example.reallogin;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

    Button memoryGameBtn, signMatchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        memoryGameBtn = findViewById(R.id.memoryGameBtn);
        signMatchBtn = findViewById(R.id.signMatchBtn);

        memoryGameBtn.setOnClickListener(v ->
                startActivity(new Intent(this, MemoryGameActivity.class)));

        signMatchBtn.setOnClickListener(v ->
                startActivity(new Intent(this, SignMatchProActivity.class)));
    }
}
