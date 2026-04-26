package com.crisispulse;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.crisispulse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTrigger.setOnClickListener(v -> startActivity(new Intent(this, TriggerActivity.class)));
        binding.btnMap.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
        binding.btnDashboard.setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        }
        }

