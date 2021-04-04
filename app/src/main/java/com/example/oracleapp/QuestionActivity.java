package com.example.oracleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oracleapp.entities.AppSettings;

import org.parceler.Parcels;

public class QuestionActivity extends AppCompatActivity implements RetainFragment.MainStateListener {
    public static final String EXTRA_SETTINGS = "SETTINGS";
    private AppSettings appSettings;
    private RetainFragment retainFragment;
    private Button button;
    private ProgressBar progress;
    private TextView answerTextView;

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
        answerTextView = findViewById(R.id.answer);
        appSettings = (AppSettings) Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SETTINGS));
        EditText editText = this.findViewById(R.id.questionEdit);

        button.setOnClickListener(v -> {
            retainFragment.generateAnswer(editText.getText().toString() + appSettings.toString());
        });
        retainFragment.setListener(this);

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
        answerTextView.setText(viewState.result);

    }
}
