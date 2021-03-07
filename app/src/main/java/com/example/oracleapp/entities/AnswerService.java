package com.example.oracleapp.entities;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class AnswerService extends Service {


    public List<String> answerList = Arrays.asList("Yes", "No", "Maybe", "Likely", "Unlikely", "No answer");

    private TestBinder binder = new TestBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String generateAnswer(String uniqueString) {
        double hashCode = uniqueString.hashCode();
        int key = (int) Math.abs(Math.round((hashCode / 2147483647) * 5));
        stopSelf();
        return answerList.get(key);
    }

    public class TestBinder extends Binder {

        public AnswerService getService() {
            return AnswerService.this;
        }
    }

}


