package com.example.oracleapp.tasks;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class GenerateAnswerCallable implements Callable<String> {

    private String str;
    private List<String> answerList = Arrays.asList("Yes", "No", "Maybe", "Likely", "Unlikely", "No answer");

    public GenerateAnswerCallable(String str) {
        this.str = str;
    }

    @Override
    public String call() throws Exception {
        double hashCode = str.hashCode();
        int key = (int) Math.abs(Math.round((hashCode / 2147483647) * 5));
        Thread.sleep(5000);
        return answerList.get(key);
    }
}
