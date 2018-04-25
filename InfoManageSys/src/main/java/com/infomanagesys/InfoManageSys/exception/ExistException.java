package com.infomanagesys.InfoManageSys.exception;

public class ExistException extends BaseException{
    private String msg;
    private int code = 1;
    private boolean success;

    public ExistException(String msg){
        super(msg);
    }
    public ExistException(String msg, Throwable throwable){
        super(msg, throwable);
    }
    @Override
    public  int getCode(){
        code = 202;
        return code;
    }
    @Override
    public boolean getSuccess(){
        success = false;
        return success;
    }
}
