import {actions} from 'mirrorx';
import * as api from "../services/Share";
export default {
    name : "share",
    initialState : {
        password:"",
        show_password:false,
        show_file:false,
        code:"",
        file:"",
        show_title:[
            {
                title:"文件分享"
            }
        ]
    },
    reducers : {
        save(state, data) {
            
            return {
                ...state,
                ...data
            }
        }
    },
    effects : {
        async getShareUrl(args){
            let {data,data:{success}}= await api.getShareUrl(args);
            if(success){
                return data;
            }
        },
        async checkShare(args){
            let {data,data:{success}} = await api.checkShare(args);
            if(success){
                let {code} = data;
                console.log(data);  
                switch(code){
                    case "0":
                    let show_password=true;
                    actions.share.save({show_password});
                    break;
                    case "1":
                    let {file}=data;
                    actions.share.save({file});
                    let show_file=true;
                    actions.share.save({show_file})
                    break;
                    case "2":
                    alert("该分享已失效");
                    break;
                    case "3":
                    alert("该分享不存在")
                    break;
                    default:
                    break;

                }
                actions.share.save({code});
            }
        },
        async getShareByPassword(args){
            let {data,data:{success}} = await api.getShareByPassword(args);
            if(success){
                let {code} = data;
                if(code=="4"){
                    
                    let {file}=data;
                    actions.share.save({file});

                    let show_file=true;
                    actions.share.save({show_file});

                    let show_password = false;
                    actions.share.save({show_password});
                }else if(code=="5"){
                    alert("密码错误")
                }
                actions.share.save({code});
            }
        }
    }
}
