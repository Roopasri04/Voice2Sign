package com.example.reallogin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class JitsiVideoCallActivity extends AppCompatActivity {

    EditText conferenceNameInput;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        conferenceNameInput = findViewById(R.id.conferenceName);
        joinBtn = findViewById(R.id.joinButton);

        joinBtn.setOnClickListener(v -> {
            String room = conferenceNameInput.getText().toString();
            if (room.length() > 0) {
                try {
                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(room)
                            .setFeatureFlag("welcomepage.enabled", false)
                            .setAudioMuted(true)
                            .setVideoMuted(true)
                            .build();
                    JitsiMeetActivity.launch(this, options);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
