package com.infomanagesys.InfoManageSys.exception.handler;

import com.infomanagesys.InfoManageSys.exception.ExistException;
import com.infomanagesys.InfoManageSys.exception.FileExistException;
import com.infomanagesys.InfoManageSys.exception.TestException;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    @ExceptionHandler(TestException.class)
    public String dealException(){
        return "Exception handle success";
    }
    @ExceptionHandler(UserCheckException.class)
    public Object dealUserCheckException(HttpServletRequest request, UserCheckException e){
        return e;
    }
    @ExceptionHandler(FileExistException.class)
    public Object dealFileExistException(HttpServletRequest request,FileExistException e){
        return e;
    }
    @ExceptionHandler(ExistException.class)
    public Object dealExistException(HttpServletRequest request,ExistException e){
        return e;
    }
}
