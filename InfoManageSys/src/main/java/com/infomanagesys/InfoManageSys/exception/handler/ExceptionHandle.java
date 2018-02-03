package com.infomanagesys.InfoManageSys.exception.handler;

import com.infomanagesys.InfoManageSys.exception.TestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(TestException.class)
    public String dealException(){
        return "Exception handle success";
    }
}
