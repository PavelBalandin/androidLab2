package com.example.oracleapp.tasks;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.Callable;

public class HandlerThreadTask<T> extends BaseTask<T> {

    private static final String THREAD_NAME = "DEFAULT";
    private static final HandlerThread THREAD = new HandlerThread(THREAD_NAME);
    private static Handler handler;

    static {
        THREAD.start();
        handler = new Handler(THREAD.getLooper());
    }

    private Callable<T> callable;
    private Runnable runnable;

    public HandlerThreadTask(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    protected void start() {
        runnable = () -> {
            try {
                T result = callable.call();
                publishSuccess(result);
            } catch (Exception e) {
                publishError(e);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onCancelled() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
            runnable = null;
        }
    }
}
