package org.example.demo0501.ddd.controller;

public class Result<T> {

    public static <T> Result<T> fail(String message){
        return new Result();
    }

    public static <T> Result<T> success(T t){
        return new Result();
    }
}
