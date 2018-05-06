import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_ROOT_ABLUM":"/photo/get/root/ablum",
    "OPEN_ABLUM":"/photo/open/ablum",
    "GET_CURRENT_ABLUM":"/photo/get/current/ablum",
    "GET_ABLUM_DATA":"/photo/get/ablum/data",
    "GET_UNCLASSFIYED_ABLUM":"/photo/get/ablum/weifenlei",
    "GET_PARENT_ABLUM":"/photo/get/parent/ablum",
    "CHANG_ABLUM_IMG":"/photo/chang/ablum/img",
    "ADD_LABEL_ABLUM":"/photo/add/label/ablum",
    "REMOVE_ABLUM_LABEL":"/photo/remove/ablum/lable",
    "ADD_LABEL_PHOTO":"/photo/add/label/photo",
    "REMOVE_PHOTO_LABEL":"/photo/remove/photo/label",
    "DELETE_ABLUM":"/photo/delete/ablum",
    "DELETE_PHOTO":"/photo/delete/photo"
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
export const addNewLabelToAblum=(args)=>{
    return request(URL.ADD_LABEL_ABLUM, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "ablumId":args.ablumId,
            "labelId":args.label.pid,
            "labelContent":args.label.content
        }
    });
}
export const removeLabelFromAblum=(args)=>{
    return request(URL.REMOVE_ABLUM_LABEL, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "ablumId":args.ablumId,
            "labelId":args.labelId
        }
    });
}
export const addNewLabelToPhoto=(args)=>{
    return request(URL.ADD_LABEL_PHOTO, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "photoId":args.photoId,
            "label":args.label
        }
    });
}
export const removeLabelFromPhoto=(args)=>{
    return request(URL.REMOVE_PHOTO_LABEL, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "photoId":args.photoId,
            "labelId":args.labelId
        }
    });
}
export const deleteAblum=(args)=>{
    return request(URL.DELETE_ABLUM, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "ablumId":args.ablumId
        }
    });
}
export const deletePhoto=(args)=>{
    return request(URL.DELETE_PHOTO, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "photoId":args.photoId
        }
    });
}