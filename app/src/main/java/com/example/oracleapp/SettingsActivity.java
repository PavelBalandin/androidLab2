package com.example.oracleapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oracleapp.entities.AppSettings;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {
    public static final String EXTRA_SETTINGS = "SETTINGS";
    private final Calendar calendar = Calendar.getInstance();
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY_SETTINGS = "SETTINGS";
    private String name;
    private String surname;
    private Date date;
    private String gender;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private EditText nameEdit;
    private EditText surnameEdit;
    private Button dateButton;
    private Button saveButton;
    private RadioGroup genderRadioGroup;
    AppSettings appSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        if (savedInstanceState != null) {
            appSettings = (AppSettings) Parcels.unwrap(savedInstanceState.getParcelable(KEY_SETTINGS));
        }

        appSettings = (AppSettings) Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SETTINGS));
        nameEdit = this.findViewById(R.id.nameEdit);
        surnameEdit = this.findViewById(R.id.surnameEdit);
        dateButton = this.findViewById(R.id.datePicker);
        saveButton = this.findViewById(R.id.saveButton);
        genderRadioGroup = this.findViewById(R.id.radioGender);

        if (appSettings != null) {
            nameEdit.setText(appSettings.getName());
            surnameEdit.setText(appSettings.getSurname());
            dateButton.setText(appSettings.getDate().toString());
            date = appSettings.getDate();
            dateButton.setText(sdf.format(date));
            if (appSettings.getGender().equals(getString(R.string.female))) {
                RadioButton radioButton = this.findViewById(R.id.radioButtonFemale);
                radioButton.setChecked(true);
            }

        }


        saveButton.setOnClickListener(v -> {
            updateData();
            if (name != null && surname != null && date != null && gender != null) {
                AppSettings appSettingsUpdated = new AppSettings(name, surname, date, gender);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_SETTINGS, Parcels.wrap(appSettingsUpdated));
                setResult(RESULT_OK, intent);
                finish();
            } else
                Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();

        });

        dateButton.setOnClickListener(v -> {
            setDate();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SETTINGS, Parcels.wrap(appSettings));
    }

    private void updateData() {
        name = nameEdit.getText().toString();
        surname = surnameEdit.getText().toString();
        int selectedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = genderRadioGroup.findViewById(selectedRadioButtonId);
        gender = radioButton.getText().toString();
    }


    public void setDate() {
        new DatePickerDialog(SettingsActivity.this, d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date = calendar.getTime();
            dateButton.setText(sdf.format(date));
        }
    };


}
