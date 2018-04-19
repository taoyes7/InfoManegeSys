import axios from "axios";
import request from "utils/request";

const URL = {
    "CREATE_DIR": "/doc/createdir",
    "OPEN_ROOT_DIR":"/doc/open/rootdir",
    "GET_CURRENT_DIR":"/doc/get/currentdir",
    "CHECK_UPLOAD_FILE":"",
    "OPEN_DIR":"/doc/open/dir",
    "BACK_PARENT":"doc/back/parent",
    "ADD_LABEL":"/doc/add/label",
    "GET_ALL_LABELS":"/doc/get/alllabels",
    "CHECK_LABEL":"/doc/check/label"
}
export const createDir = (sessionId,dirName) => {
   return request(URL.CREATE_DIR, {
        method: "post", 
        param: {
            "sessionId":sessionId,
            "name":dirName
        }
    });
}
export const openRootDir = (sessionId) =>{
    return request(URL.OPEN_ROOT_DIR,{
        method:"post",
        param:{
            "sessionId":sessionId
        }
    });
}
export const openDir=(args)=>{
    return request(URL.OPEN_DIR,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "dirId":args.dirId
        }
    })
}
export const getCurrentDir = (sessionId) =>{
    return request(URL.GET_CURRENT_DIR,{
        method:"post",
        param:{
            "sessionId":sessionId
        }
    });
}
export const checkUpLoadFile=(sessionId,fileName)=>{
    return request(URL.CHECK_UPLOAD_FILE,{
        method:"post",
        param:{
            "sessionId":sessionId,
            "fileName":fileName
        }
    });
}
export const backToParent=(sessionId)=>{
    return request(URL.BACK_PARENT,{
        method:"post",
        param:{
            "sessionId":sessionId
        }
    });
}
export const addLabel=(args)=>{
    console.log(args);
    return request(URL.ADD_LABEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "file":args.file,
            "label":args.label
        }
    });
}
export const getAllLabels=(args)=>{
    return request(URL.GET_ALL_LABELS,{
        method:"post",
        param:{
            "sessionId":args.sessionId
        }
    });
} 
export const checkLabel=(args)=>{
    return request(URL.CHECK_LABEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "label":args.label
        }
    });
} 
