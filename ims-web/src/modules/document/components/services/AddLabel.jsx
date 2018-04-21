import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_ALL_LABELS":"/doc/get/alllabels",
    "CHECK_LABEL":"/doc/check/label",
    "ADD_LABEL":"/doc/add/label"
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
    

