package com.example.oracleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.oracleapp.entities.AppSettings;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_SETTINGS = "SETTINGS";

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int RQ_SETTINGS = 1;
    private AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        if (savedInstanceState != null) {
            appSettings = savedInstanceState.getParcelable(KEY_SETTINGS);
        }


        Button questionButton = this.findViewById(R.id.askButton);
        Button settingsButton = this.findViewById(R.id.settingsButton);
        Button exitButton = this.findViewById(R.id.exitButton);

        questionButton.setOnClickListener(v -> {
            if (appSettings != null) {
                Intent intent = new Intent(this, QuestionActivity.class);
                intent.putExtra(QuestionActivity.EXTRA_SETTINGS, appSettings);
                startActivityForResult(intent, RQ_SETTINGS);
            } else
                Toast.makeText(this, "You must fill the settings form!", Toast.LENGTH_SHORT).show();
        });

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(SettingsActivity.EXTRA_SETTINGS, appSettings);
            startActivityForResult(intent, RQ_SETTINGS);
        });

        exitButton.setOnClickListener(v -> {
            System.exit(0);
        });

        Log.d(TAG, "Got new settings: " + appSettings);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SETTINGS, appSettings);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_SETTINGS && resultCode == RESULT_OK) {
            appSettings = data.getParcelableExtra(SettingsActivity.EXTRA_SETTINGS);
            Log.d(TAG, "Got new settings: " + appSettings);
        }
    }
}