import axios from "axios";
import request from "utils/request";

const URL = {
    "CREATE_ALBUM":"/photo/create/album"
}

export const createAblum=(args)=>{
    return request(URL.CREATE_ALBUM, {
        method: "post", 
        param: {
            "label" : args.label,
            "sessionId":args.sessionId,
            "name":args.name,
            "description":args.description
        }
    });
}



