import axios from "axios";
import request from "utils/request";

const URL = {
    "REGISTER": "/user/register",
    "LOGIN":"/user/login",
    "CREATE_ROOT_DIR":"/doc/create/rootdir"
    
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
export const createRootDir=(sessionId)=>{
    return request(URL.CREATE_ROOT_DIR, {
        method: "post", 
        param: {
            "sessionId":sessionId
        }
    });
}



