import axios from "axios";
import request from "utils/request";

const URL = {
    "CREATE_LABEL_TYPE": "/doc/create/label/type",
    "GET_ALL_LABELTYPES":"/doc/get/all/labeltypes",
    "GET_ALL_LABELS_GROUP":"/doc/get/alllabels/group",
    "CHANGE_LABEL_TYPE":"/doc/change/labeltype",
    "DELETE_LABEL":"/doc/delete/label/label",
    "NEW_LABEL":"/doc//new/label",
    "DELETE_LABELTYPE":"/doc/delete/labeltype"
}
export const newLabelType = (args) => {
   return request(URL.CREATE_LABEL_TYPE, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "name":args.name,
            "description":args.description
        }
    });
}
export const getAllLabelType = (args) => {
    return request(URL.GET_ALL_LABELTYPES, {
         method: "post", 
         param: {
             "sessionId":args.sessionId
         }
     });
 }
 export const getAllLabelsByGroup = (args) => {
    return request(URL.GET_ALL_LABELS_GROUP, {
         method: "post", 
         param: {
             "sessionId":args.sessionId
         }
     });
 }
 export const changeLabelType = (args) => {
    return request(URL.CHANGE_LABEL_TYPE, {
         method: "post", 
         param: {
            "sessionId":args.sessionId,
            "type":args.type,
            "label":args.change_label
         }
     });
 }
 export const deleteLabel = (args) => {
    return request(URL.DELETE_LABEL, {
         method: "post", 
         param: {
            "sessionId":args.sessionId,
            "label":args.change_label
         }
     });
 }
 export const newLabel = (args) => {
    return request(URL.NEW_LABEL, {
         method: "post", 
         param: {
            "sessionId":args.sessionId,
            "name":args.newLabel_name,
            "describe":args.newLabel_describe,
            "labelType":args.new_label_type
         }
     });
 }
 export const deleteLabelType = (args) => {
    return request(URL.DELETE_LABELTYPE, {
         method: "post", 
         param: {
            "sessionId":args.sessionId,
            "labelTypeId":args.delete_LabelType.pid,
         }
     });
 }