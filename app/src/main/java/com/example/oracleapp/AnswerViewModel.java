package com.example.oracleapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnswerViewModel extends ViewModel {
    private MutableLiveData<String> answer;

    public MutableLiveData<String> getCurrentName() {
        if (answer == null) {
            answer = new MutableLiveData<String>();
        }
        return answer;
    }
}

