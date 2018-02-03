package com.infomanagesys.InfoManageSys.exception;

public class TestException extends BaseException {
    private String msg;
    private int code = 1;

    public TestException(String msg){
        super(msg);
    }
    public TestException(String msg, Throwable throwable){
        super(msg, throwable);
    }
    @Override
    public  int getCode(){
        code = 1001;
        return code;
    }

}
