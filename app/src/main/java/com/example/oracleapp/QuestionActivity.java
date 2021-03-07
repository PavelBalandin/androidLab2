package com.example.oracleapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oracleapp.entities.AnswerService;
import com.example.oracleapp.entities.AppSettings;

public class QuestionActivity extends AppCompatActivity {
    private AppSettings appSettings;
    public static final String EXTRA_SETTINGS = "SETTINGS";

    private AnswerService service;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            QuestionActivity.this.service = ((AnswerService.TestBinder) binder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);
        appSettings = getIntent().getParcelableExtra(EXTRA_SETTINGS);
        EditText editText = this.findViewById(R.id.questionEdit);
        Intent intent = new Intent(this, AnswerService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        findViewById(R.id.answerButton).setOnClickListener(v -> {
            if (service == null) return;
            String answer = service.generateAnswer(editText.getText().toString() + appSettings.toString());
            Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AnswerService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

}
