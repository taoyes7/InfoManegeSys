import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_CLASSFIYED_FILEGROUPS": "/doc/get/classfiyed/file"
}
export const getClassfiyedFileGroups = (args) => {
   return request(URL.GET_CLASSFIYED_FILEGROUPS, {
        method: "post", 
        param: {
            "sessionId":args.sessionId,
            "name":args.dirId
        }
    });
}