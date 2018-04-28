import axios from "axios";
import request from "utils/request";

const URL = {
    "CREATE_DIR": "/doc/createdir",
    "OPEN_ROOT_DIR":"/doc/open/rootdir",
    "GET_CURRENT_DIR":"/doc/get/currentdir",
    "CHECK_UPLOAD_FILE":"",
    "OPEN_DIR":"/doc/open/dir",
    "BACK_PARENT":"doc/back/parent",
    "ADD_FILE_LABEL":"/doc/add/file/label",
    "DELETE_FILE_LABEL":"/doc/delete/file/label",
    "DOWNLOAD_FILE":"/doc/download/file",
    "DELETE_FILE":"/doc/delete/file",
    "GET_RULES_FILES":"/doc/get/classfiyed/file"
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
export const addLabelToFile=(args)=>{
    return request(URL.ADD_FILE_LABEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "label":args.label,
            "file":args.file
        }
    });
}
export const deleteLabelFromFile=(args)=>{
    return request(URL.DELETE_FILE_LABEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "labelId":args.labelId,
            "file":args.file
        }
    });
}
export const downLoadFile=(args)=>{
    return request(URL.DOWNLOAD_FILE,{
        method:"get",
        param:{
            "sessionId":args.sessionId,
            "fileId":args.fileId
        }
    });
}
export const deleteFile=(args)=>{
    return request(URL.DELETE_FILE,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "fileId":args.fileId
        }
    });
}
export const getRulesAndFiles=(args)=>{
    return request(URL.GET_RULES_FILES,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "dirId":args.dirId
        }
    });
}

