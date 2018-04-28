import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_SHARE_URL": "/doc/share/file",
    "CHECK_SHARE": "/doc/check/share",
    "GET_SHARE_PASSWORD":"/doc/get/share/file"
}

export const getShareUrl=(args)=>{
    return request(URL.GET_SHARE_URL, {
        method: "post", 
        param: {
            "fileId":args.fileId,
            "sessionId":args.sessionId,
            "isPrivate":args.isPrivate,
            "shareTypeCode":args.shareTypeCode,
        }
    });
}
export const checkShare=(args)=>{
    return request(URL.CHECK_SHARE, {
        method: "post", 
        param: {
            "shareId":args.shareId
        }
    });
}
export const getShareByPassword=(args)=>{
    return request(URL.GET_SHARE_PASSWORD, {
        method: "post", 
        param: {
            "shareId":args.shareId,
            "password":args.password
        }
    });
}



