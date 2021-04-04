package com.example.oracleapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oracleapp.entities.AnswerService;
import com.example.oracleapp.entities.AppSettings;

import org.parceler.Parcels;

public class QuestionActivity extends AppCompatActivity implements RetainFragment.MainStateListener {
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

    private RetainFragment retainFragment;

    private Button button;
    private ProgressBar progress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);
        retainFragment = (RetainFragment) getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        if (retainFragment == null) {
            retainFragment = new RetainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(retainFragment, RetainFragment.TAG)
                    .commit();
        }
        button = findViewById(R.id.answerButton);
        progress = findViewById(R.id.progress);
        appSettings = (AppSettings) Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SETTINGS));
        EditText editText = this.findViewById(R.id.questionEdit);
        Intent intent = new Intent(this, AnswerService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        button.setOnClickListener(v -> {
            retainFragment.generateAnswer(editText.getText().toString() + appSettings.toString());
//            if (service == null) return;
//            String answer = service.generateAnswer(editText.getText().toString() + appSettings.toString());
//            Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
        });
        retainFragment.setListener(this);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retainFragment.setListener(null);
    }

    @Override
    public void onNewState(ViewState viewState) {
        button.setEnabled(viewState.isButtonEnabled);
        progress.setVisibility(viewState.showProgress ? View.VISIBLE : View.GONE);
        if (!viewState.result.equals("")) {
            Toast.makeText(this, viewState.result, Toast.LENGTH_SHORT).show();
        }
    }
}
