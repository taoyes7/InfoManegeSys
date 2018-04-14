package com.infomanagesys.InfoManageSys.exception;

public class FileExistException extends BaseException{
    private String msg;
    private int code = 1;
    private boolean success;

    public FileExistException(String msg){
        super(msg);
    }
    public FileExistException(String msg, Throwable throwable){
        super(msg, throwable);
    }
    @Override
    public  int getCode(){
        code = 201;
        return code;
    }
    @Override
    public boolean getSuccess(){
        success = false;
        return success;
    }
}
