import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_LABEL_FILETYPE":"/doc/get/curdir/labelandtype",
    "SELECT":"/doc/select/files"
}
export const loadFileTypesAndLabels = (args) => {
   return request(URL.GET_LABEL_FILETYPE, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "dirId":args.dirId
        }
    });
}
export const select = (args) => {
    return request(URL.SELECT, {
         method: "post", 
         param: {
             "sessionId":args.sessionId,
             "dirId":args.dirId,
             "label":args.label,
             "fileType":args.fileType
         }
     });
 }