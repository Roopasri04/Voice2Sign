package com.example.reallogin;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import okhttp3.*;

public class TranslationActivity extends AppCompatActivity {

    private EditText inputText;
    private Spinner languageSpinner;
    private TextView translatedText;
    private Button translateButton;

    private final Map<String, String> languageMap = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        inputText = findViewById(R.id.inputText);
        languageSpinner = findViewById(R.id.languageSpinner);
        translatedText = findViewById(R.id.translatedText);
        translateButton = findViewById(R.id.translateButton);

        setupLanguageSpinner();

        translateButton.setOnClickListener(v -> {
            String text = inputText.getText().toString().trim();
            String targetLangCode = languageMap.get(languageSpinner.getSelectedItem().toString());

            if (!text.isEmpty() && targetLangCode != null) {
                translateText(text, "en", targetLangCode);  // default source = English
            } else {
                translatedText.setText("Please enter text and select language.");
            }
        });
    }

    private void setupLanguageSpinner() {
        languageMap.put("Tamil", "ta");
        languageMap.put("Hindi", "hi");
        languageMap.put("Spanish", "es");
        languageMap.put("French", "fr");
        languageMap.put("German", "de");
        languageMap.put("Japanese", "ja");
        languageMap.put("Russian", "ru");
        languageMap.put("Chinese", "zh");
        languageMap.put("Italian", "it");
        languageMap.put("Korean", "ko");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(languageMap.keySet()));
        languageSpinner.setAdapter(adapter);
    }

    private void translateText(String text, String from, String to) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.mymemory.translated.net/get?q=" + text + "&langpair=" + from + "|" + to;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> translatedText.setText("Translation failed: " + e.getMessage()));
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        JSONObject json = new JSONObject(result);
                        String translated = json.getJSONObject("responseData").getString("translatedText");
                        runOnUiThread(() -> translatedText.setText(translated));
                    } catch (JSONException e) {
                        runOnUiThread(() -> translatedText.setText("Error parsing translation."));
                    }
                } else {
                    runOnUiThread(() -> translatedText.setText("Translation failed."));
                }
            }
        });
    }
}