package com.example.oracleapp.tasks;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class GenerateAnswerCallable implements Callable<String> {
    private String input;
    private static final List<String> answerList = Arrays.asList("Yes", "No", "Maybe", "Likely", "Unlikely", "No answer");

    public GenerateAnswerCallable(String input) {
        this.input = input;
    }

    @Override
    public String call() throws Exception {
        double hashCode = input.hashCode();
        int key = (int) Math.abs(Math.round((hashCode / 2147483647) * 5));
        Thread.sleep(1000); // For progress bar test
        return answerList.get(key);
    }
}
