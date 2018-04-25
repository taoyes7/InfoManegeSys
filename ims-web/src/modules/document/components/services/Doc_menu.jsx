import axios from "axios";
import request from "utils/request";

const URL = {
    "CREATE_LABEL_TYPE": "/doc/create/label/type",
    "GET_ALL_LABELTYPES":"/doc/get/all/labeltypes",
    "GET_ALL_LABELS_GROUP":"/doc/get/alllabels/group"
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