package com.example.oracleapp.tasks;

public interface Task<T> {
    void execute(TaskListener<T> listener);

    void cancel();
}
