import axios from "axios";
import request from "utils/request";

const URL = {
    "GET_USER": "https://mock.yonyoucloud.com/mock/56/nc/api/user",
    "TEST_AXIOS" : "/test/Axios",
    "GET_USERINFO":"/user/get/userInfo",
    "CHANGE_USERINFO_IMG":"/user/change/userInfo/img",
    "CHANGE_USER_INFO":"/user/change/userInfo",
    "GET_ALL_USERLINKS":"/user/get/userLinks",
    "CREATE_USERLINK":"/user/create/userLink",
    "DELETE_USERlINK":"/user/delete/userLink",
    "CREATE_DIARY":"/user/create/userDiary",
    "SAVE_DIARY":"/user/change/userDiary",
    "GET_USERDIARY":"/user/get/userDiary",
    "SAVE_PLAN":"/user/create/userPlan",
    "END_PLAN":"/user/end/userPlan",
    "GET_PLANS":"/user/get/userPlan"
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
export const loadUserInfo = (args) => {
    return request(URL.GET_USERINFO, {
        method: "post",
        param: {
            "sessionId":args.sessionId
        }
    });
}
export const changeUserInfo_Img = (args) => {
    return request(URL.CHANGE_USERINFO_IMG, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "photoId":args.photoId
        }
    });
}
export const changeUserInfo = (args) => {
    return request(URL.CHANGE_USER_INFO, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "userInfo":args.userInfo
        }
    });
}
export const getAllUserLinkS = (args) => {
    return request(URL.GET_ALL_USERLINKS, {
        method: "post",
        param: {
            "sessionId":args.sessionId
        }
    });
}
export const createUserLink = (args) => {
    return request(URL.CREATE_USERLINK, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "userLink":args.userLink
        }
    });
}
export const deleteUserLink = (args) => {
    return request(URL.DELETE_USERlINK, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "userLinkId":args.userLinkId
        }
    });
}
export const createDiary = (args) => {
    return request(URL.CREATE_DIARY, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "title":args.title,
            "content":args.content
        }
    });
}
export const saveDiary = (args) => {
    return request(URL.SAVE_DIARY, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "title":args.title,
            "content":args.content,
            "diaryId":args.diaryId
        }
    });
}
export const getUserDiaryS = (args) => {
    return request(URL.GET_USERDIARY, {
        method: "post",
        param: {
            "sessionId":args.sessionId
        }
    });
}
export const savePlan = (args) => {
    console.log(args);
    return request(URL.SAVE_PLAN, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "title":args.title,
            "content":args.content,
            "level":args.level
        }
    });
}
export const endPlan = (args) => {
    return request(URL.END_PLAN, {
        method: "post",
        param: {
            "sessionId":args.sessionId,
            "planId":args.userPlan.pid
        }
    });
}
export const getUserPlanS = (args) => {
    return request(URL.GET_PLANS, {
        method: "post",
        param: {
            "sessionId":args.sessionId
        }
    });
}
     
