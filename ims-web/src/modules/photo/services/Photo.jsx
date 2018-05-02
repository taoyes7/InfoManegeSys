import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_ROOT_ABLUM":"/photo/get/root/ablum",
    "OPEN_ABLUM":"/photo/open/ablum",
    "GET_CURRENT_ABLUM":"/photo/get/current/ablum",
    "GET_ABLUM_DATA":"/photo/get/ablum/data",
    "GET_UNCLASSFIYED_ABLUM":"/photo/get/ablum/weifenlei",
    "GET_PARENT_ABLUM":"/photo/get/parent/ablum",
    "CHANG_ABLUM_IMG":"/photo/chang/ablum/img"
}

export const getRootAblum=(args)=>{
    return request(URL.GET_ROOT_ABLUM, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
        }
    });
}
export const openAblum=(args)=>{
    return request(URL.OPEN_ABLUM, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "ablumId":args.ablumId
        }
    });
}
export const getCurrentAblum=(args)=>{
    return request(URL.GET_CURRENT_ABLUM, {
        method: "post", 
        param: {
            "sessionId":args.sessionId
        }
    });
}
export const getAblumData=(args)=>{
    return request(URL.GET_ABLUM_DATA, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "ablumId":args.ablumId  
        }
    });
}
export const getUnClassfiyedAblum=(args)=>{
    return request(URL.GET_UNCLASSFIYED_ABLUM, {
        method: "post", 
        param: {
            "sessionId":args.sessionId
        }
    });
}
export const getParentAblum=(args)=>{
    return request(URL.GET_PARENT_ABLUM, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "ablumId":args.ablumId
        }
    });
}
export const changeAblumImg=(args)=>{
    return request(URL.CHANG_ABLUM_IMG, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "ablumId":args.ablumId,
            "photoId":args.photoId
        }
    });
}