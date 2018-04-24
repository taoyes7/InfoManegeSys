import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_ALL_LABELS":"/doc/get/alllabels",
    "CHECK_LABEL":"/doc/check/label",
    "ADD_LABEL":"/doc/add/label",
    "ADD_RULES":"/doc/add/rules",
    "GET_FILR_RULES":"/doc/get/classfiyrules",
    "DELETE_RULES":"/doc/delete/classfiyrules",
    "DELETE_LABEL":"/doc/delete/label",
    "DELETE_FILETYPE":"/doc/delete/filetype",
    "ADD_SINGLE_LABEL":"/doc/add/singlelabel",
    "ADD_SINGLE_FILETYPE":"/doc/add/singlefiletype",
    "EXCHANGE_LEVEL":"/doc/exchange/level"
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
    return request(URL.ADD_LABEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "file":args.file,
            "label":args.label
        }
    });
}
export const addRules=(args)=>{
    console.log(args);
    return request(URL.ADD_RULES,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "dir":args.dir,
            "label":args.label,
            "ruleName":args.ruleName,
            "fileType":args.fileType
        }
    });
}

export const getFileRules=(args)=>{
    return request(URL.GET_FILR_RULES,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "dirId":args.dirId
        }
    });
}

export const deleteRules=(args)=>{
    return request(URL.DELETE_RULES,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "ruleId":args.ruleId,
            "classfiyId":args.classfiyId
        }
    });
}
export const deleteLabel=(args)=>{
    return request(URL.DELETE_LABEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "labelId":args.labelId,
            "labelGroupId":args.labelGroup.pid
        }
    });
}
export const deleteFileType=(args)=>{
    return request(URL.DELETE_FILETYPE,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "fileType":args.fileType,
            "labelGroupId":args.labelGroup.pid
        }
    });
}
export const addSingleLabel=(args)=>{
    return request(URL.ADD_SINGLE_LABEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "label":args.label,
            "labelGroupId":args.labelGroup.pid
        }
    });
}
export const addSingleFileType=(args)=>{
    return request(URL.ADD_SINGLE_FILETYPE,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "fileType":args.fileType,
            "labelGroupId":args.labelGroup.pid
        }
    });
}
export const exchangLevel=(args)=>{
    console.log(args);
    return request(URL.EXCHANGE_LEVEL,{
        method:"post",
        param:{
            "sessionId":args.sessionId,
            "curPid":args.curPid,
            "nextPid":args.nextPid,
            "classfiyRuleId":args.classfiyRuleId
        }
    });
}


