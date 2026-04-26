package com.crisispulse;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.crisispulse.databinding.ActivityTriggerBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class TriggerActivity extends AppCompatActivity {
    private ActivityTriggerBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriggerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.btnSubmit.setOnClickListener(v -> submitTrigger());
    }

    private void submitTrigger() {
        int selectedId = binding.rgType.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a crisis type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        String type = rb.getText().toString().toLowerCase();
        String description = binding.etDescription.getText().toString();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                sendToFirebase(type, description, location.getLatitude(), location.getLongitude());
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendToFirebase(String type, String description, double lat, double lng) {
        String triggerId = mDatabase.child("triggers").push().getKey();
        Map<String, Object> trigger = new HashMap<>();
        trigger.put("type", type);
        trigger.put("description", description);
        trigger.put("latitude", lat);
        trigger.put("longitude", lng);
        trigger.put("timestamp", System.currentTimeMillis());
        trigger.put("status", "pending");

        mDatabase.child("triggers").child(triggerId).setValue(trigger)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Signal Sent! AI is validating...", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to send signal", Toast.LENGTH_SHORT).show());
    }
}
