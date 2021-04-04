package com.example.oracleapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oracleapp.tasks.GenerateAnswerCallable;
import com.example.oracleapp.tasks.HandlerThreadTask;
import com.example.oracleapp.tasks.Task;
import com.example.oracleapp.tasks.TaskListener;

public class RetainFragment extends Fragment {

    public static final String TAG = RetainFragment.class.getSimpleName();

    private ViewState viewState = new ViewState();
    private MainStateListener listener;

    private Task<String> currentTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setListener(MainStateListener listener) {
        this.listener = listener;
        if (listener != null) {
            listener.onNewState(viewState);
        }
    }

    public void generateAnswer(String string) {
        currentTask = createGenerateAnswerTask(string);

        viewState.isButtonEnabled = false;
        viewState.showResult = false;
        viewState.result = "";
        viewState.showProgress = true;
        updateState();
        currentTask.execute(new TaskListener<String>() {
            @Override
            public void onSuccess(String result) {
                viewState.isButtonEnabled = true;
                viewState.showResult = true;
                viewState.result = result;
                viewState.showProgress = false;
                updateState();
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Error!", error);
                viewState.isButtonEnabled = true;
                viewState.showResult = false;
                viewState.result = "";
                viewState.showProgress = false;
                updateState();
            }
        });
    }

    private void updateState() {
        if (listener != null) {
            listener.onNewState(viewState);
        }
    }

    private Task<String> createGenerateAnswerTask(String string) {
        return new HandlerThreadTask<>(new GenerateAnswerCallable(string));
    }

    public interface MainStateListener {
        void onNewState(ViewState viewState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (currentTask != null) {
            currentTask.cancel();
        }
    }
}
