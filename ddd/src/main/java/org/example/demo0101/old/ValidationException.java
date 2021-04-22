package org.example.demo0101.old;

public class ValidationException extends Exception {

    //异常信息
    private String message;

    //构造函数
    public ValidationException(String message){
        super(message);
        this.message = message;
    }
}
