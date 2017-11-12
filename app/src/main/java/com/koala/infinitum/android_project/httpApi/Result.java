package com.koala.infinitum.android_project.httpApi;

/**
 * Created by andrey on 12.11.17.
 */

public class Result<T> {
    private T answer;

    private String err;

    public Result(T answer, String err){
        this.err=err;
        this.answer=answer;
    }

    public Result(){};

    public void setAnswer(T answer){
        this.answer=answer;
    }

    public void setErr(String err){
        this.err=err;
    }

    public T getAnswer(){ return answer;}

    public String getErr(){return err;}
}
