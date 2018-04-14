package com.infomanagesys.InfoManageSys.exception;

public class UserCheckException  extends BaseException{
    private String msg;
    private int code = 1;
    private boolean success;

    public UserCheckException(String msg){
        super(msg);
    }
    public UserCheckException(String msg, Throwable throwable){
        super(msg, throwable);
    }
    @Override
    public  int getCode(){
        code = 001;
        return code;
    }
    @Override
    public boolean getSuccess(){
        success = false;
        return success;
    }
}
