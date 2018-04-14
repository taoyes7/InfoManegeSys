package com.infomanagesys.InfoManageSys.exception;

public abstract class BaseException extends RuntimeException{
    private String msg;
    private boolean success;
    private int code = 1;

    BaseException(String msg){
        super(msg);
    }
    BaseException(String msg, Throwable throwable){
        super(msg, throwable);
    }
    public abstract boolean getSuccess();
    public abstract int getCode();
}
