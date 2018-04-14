import axios from "axios";
import request from "utils/request";

const URL = {
    "REGISTER": "/user/register",
    "LOGIN":"/user/login"
    
}
export const register = () => {

   return request(URL.REGISTER, {
        method: "post", 
        param: {
            "accountid":"18010541632",
            "passworld":"123456"
        }
    });
}
export const login = () =>{
    return request(URL.LOGIN, {
        method: "post", 
        param: {
            "accountid":"18010541632",
            "passworld":"123456"
        }
    });
}



