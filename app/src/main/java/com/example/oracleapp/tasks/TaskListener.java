package com.example.oracleapp.tasks;

public interface TaskListener<T> {
    void onSuccess(T result);

    void onError(Throwable error);
}
