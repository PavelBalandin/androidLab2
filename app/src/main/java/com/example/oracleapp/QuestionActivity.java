package com.example.oracleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.oracleapp.entities.AppSettings;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    public static final String EXTRA_SETTINGS = "SETTINGS";
    private AppSettings appSettings;
    private Button button;
    private TextView answerTextView;
    private AnswerViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);

        button = findViewById(R.id.answerButton);
        answerTextView = findViewById(R.id.answer);
        appSettings = (AppSettings) Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SETTINGS));
        EditText editText = this.findViewById(R.id.questionEdit);

        button.setOnClickListener(v -> {
            String another = generateAnswer(editText.getText().toString() + appSettings.toString());
            model.getCurrentName().setValue(another);
        });


        model = new ViewModelProvider(this).get(AnswerViewModel.class);

        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                answerTextView.setText(newName);
            }
        };

        model.getCurrentName().observe(this, nameObserver);

    }


    public String generateAnswer(String input) {
        List<String> answerList = Arrays.asList(getString(R.string.yes),
                getString(R.string.no),
                getString(R.string.maybe),
                getString(R.string.likely),
                getString(R.string.unlikely),
                getString(R.string.noanswer)
        );
        double hashCode = input.hashCode();
        int key = (int) Math.abs(Math.round((hashCode / 2147483647) * 5));
        return answerList.get(key);
    }
}
