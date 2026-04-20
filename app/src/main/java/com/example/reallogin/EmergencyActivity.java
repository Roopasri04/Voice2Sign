package com.example.reallogin;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class EmergencyActivity extends Activity {
    FusedLocationProviderClient fusedLocationClient;
    private static final int PICK_CONTACT = 1;
    String selectedPhoneNumber = "";
    TextView contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        contactNumber = findViewById(R.id.contactNumber);
        Button pickContact = findViewById(R.id.pickContact);
        Button sendSOS = findViewById(R.id.sendSOS);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION},
                1);

        pickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        sendSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedPhoneNumber.isEmpty()) {
                    if (ContextCompat.checkSelfPermission(EmergencyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(EmergencyActivity.this, location -> {
                                    String locationMsg = "";
                                    if (location != null) {
                                        double lat = location.getLatitude();
                                        double lon = location.getLongitude();
                                        locationMsg = "My Location: http://maps.google.com/?q=" + lat + "," + lon;
                                    } else {
                                        locationMsg = "Location not available.";
                                    }

                                    String message = "I am in danger. Please help me immediately!\n" + locationMsg;

                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(selectedPhoneNumber, null, message, null, null);
                                    Toast.makeText(EmergencyActivity.this, "SOS Message Sent!", Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(EmergencyActivity.this, "Location permission not granted!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EmergencyActivity.this, "No contact selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                selectedPhoneNumber = cursor.getString(phoneIndex);
                contactNumber.setText("Selected: " + selectedPhoneNumber);
                cursor.close();
            }
        }
    }
}