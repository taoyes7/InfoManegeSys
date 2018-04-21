import axios from "axios";
import request from "utils/request";

const URL = {
    "CREATE_DIR": "/doc/createdir"
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

