import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_USER": "https://mock.yonyoucloud.com/mock/56/nc/api/user",
    "TEST_AXIOS" : "/test/Axios"
}
export const CallApi = (message) => {

//    axios.get(URL.TEST_AXIOS).then(res=>{
//     //    console.log(res);
//        let {data:{data,success}} = res;
//        alert(data+success);
//    }).catch((error)=>{
//        alert(error)
//    })
   return request(URL.TEST_AXIOS, {
        method: "post", 
        param: {
            "data":message
        }
    });
}
export const get = () => {
    return request(URL.GET_USER, {
        method: "get"
    });
}

