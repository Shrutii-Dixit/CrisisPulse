package com.crisispulse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.crisispulse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 🔥 LOCATION PERMISSION CHECK
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        // Buttons
        binding.btnTrigger.setOnClickListener(v ->
                startActivity(new Intent(this, TriggerActivity.class)));

        binding.btnMap.setOnClickListener(v ->
                startActivity(new Intent(this, MapActivity.class)));

        binding.btnDashboard.setOnClickListener(v ->
                startActivity(new Intent(this, DashboardActivity.class)));
    }
}