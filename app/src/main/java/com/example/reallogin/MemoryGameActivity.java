package com.example.reallogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MemoryGameActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, startBtn;
    TextView levelText, highScoreText;

    ArrayList<Integer> pattern = new ArrayList<>();
    ArrayList<Integer> userInput = new ArrayList<>();

    Random random = new Random();
    Handler handler = new Handler();

    int level = 1;
    int highScore = 0;
    boolean isPlaying = false;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        startBtn = findViewById(R.id.startBtn);
        levelText = findViewById(R.id.levelText);
        highScoreText = findViewById(R.id.highScoreText);

        preferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        highScore = preferences.getInt("HIGH_SCORE", 0);
        highScoreText.setText("High Score: " + highScore);

        setButtonsEnabled(false);

        startBtn.setOnClickListener(v -> showInstructions());

        View.OnClickListener listener = view -> {
            if (isPlaying) return;

            animatePress((Button) view);

            int value = getButtonIndex(view);
            userInput.add(value);
            checkInput();
        };

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
    }

    // ===============================
    // Instructions Dialog
    // ===============================
    private void showInstructions() {
        new AlertDialog.Builder(this)
                .setTitle("How to Play")
                .setMessage("1. Watch the sequence carefully.\n\n" +
                        "2. Repeat the same order.\n\n" +
                        "3. Each level adds one more step.\n\n" +
                        "Try to beat the high score!")
                .setPositiveButton("Start Game", (dialog, which) -> {
                    level = 1;
                    pattern.clear();
                    startGame();
                })
                .setCancelable(false)
                .show();
    }

    // ===============================
    // Start Game
    // ===============================
    private void startGame() {
        userInput.clear();
        setButtonsEnabled(true);

        pattern.add(random.nextInt(4));
        levelText.setText("Level: " + level);

        playPattern();
    }

    // ===============================
    // Smooth Animation Play Pattern
    // ===============================
    private void playPattern() {
        isPlaying = true;

        for (int i = 0; i < pattern.size(); i++) {
            int index = pattern.get(i);
            Button btn = getButton(index);

            handler.postDelayed(() -> animateFlash(btn), i * 800);
        }

        handler.postDelayed(() -> isPlaying = false,
                pattern.size() * 800);
    }

    // Smooth flash animation
    private void animateFlash(Button btn) {
        btn.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() ->
                        btn.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(200)
                                .start()
                ).start();
    }

    // Button press animation
    private void animatePress(Button btn) {
        btn.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(100)
                .withEndAction(() ->
                        btn.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start()
                ).start();
    }

    // ===============================
    // Check User Input
    // ===============================
    private void checkInput() {
        int index = userInput.size() - 1;

        if (!userInput.get(index).equals(pattern.get(index))) {
            gameOver();
            return;
        }

        if (userInput.size() == pattern.size()) {
            level++;
            handler.postDelayed(this::startGame, 1000);
        }
    }

    // ===============================
    // Game Over + Save High Score
    // ===============================
    private void gameOver() {

        setButtonsEnabled(false);

        int score = level - 1;

        if (score > highScore) {
            highScore = score;
            preferences.edit().putInt("HIGH_SCORE", highScore).apply();
            highScoreText.setText("High Score: " + highScore);
        }

        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Your Score: " + score +
                        "\nHigh Score: " + highScore)
                .setPositiveButton("Restart", (dialog, which) -> {
                    level = 1;
                    pattern.clear();
                    startGame();
                })
                .setNegativeButton("Exit", null)
                .show();
    }

    // ===============================
    // Helpers
    // ===============================
    private Button getButton(int index) {
        switch (index) {
            case 0: return btn1;
            case 1: return btn2;
            case 2: return btn3;
            default: return btn4;
        }
    }

    private int getButtonIndex(View view) {
        if (view.getId() == R.id.btn1) return 0;
        if (view.getId() == R.id.btn2) return 1;
        if (view.getId() == R.id.btn3) return 2;
        return 3;
    }

    private void setButtonsEnabled(boolean enabled) {
        btn1.setEnabled(enabled);
        btn2.setEnabled(enabled);
        btn3.setEnabled(enabled);
        btn4.setEnabled(enabled);
    }
}