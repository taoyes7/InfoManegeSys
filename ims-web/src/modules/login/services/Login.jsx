import axios from "axios";
import request from "utils/request";

const URL = {
    "REGISTER": "/user/register",
    "LOGIN":"/user/login",
    "CREATE_ROOT_DIR":"/doc/create/rootdir"
    
}
export const register = (args) => {

   return request(URL.REGISTER, {
        method: "post", 
        param: {
            "userInfo":args.userInfo
        }
    });
}
export const login = (args) =>{
    console.log(args);
    return request(URL.LOGIN, {
        method: "post", 
        param: {
            "accountid":args.accountid,
            "passworld":args.passworld
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



