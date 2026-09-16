package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiringAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchExpiringAlerts = findViewById(R.id.switchExpiringAlerts);

        switchExpiringAlerts.setChecked(true);
    }
}