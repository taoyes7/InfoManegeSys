import {actions} from 'mirrorx';
import * as api from "../services/Login";
export default {
    name : "login",
    initialState : {
       sessionId:"",
       state:true,
       severPath:"http://localhost:8085",
       accountId:"",
       password:"",
       userInfo:{
           name:"",
           job:"",
           job_date:"",
           motto:"",
           qq:"",
           e_mail:"",
           address:"",
           phone:"",
           password:"",
           ac_password:""
       }
    },
    reducers : {
        save(state, data) {
            
            return {
                ...state,
                ...data
            }
        }, 
        getSessionId(state){
            
        }
    },
    effects : {
        async register(args){
             let  {data:success} = await api.register(args);
            if(success){
                alert("注册成功");
                actions.routing.push({
                    pathname: `/login`
                })
            }
        },
        async login(args){
            let {data:{state,sessionId}} = await api.login(args);
            if(state==true){
                actions.login.save({state});
                actions.login.save({sessionId});
                actions.routing.push({
                    pathname: `/home`
                });
            }
        }
        // async createRootDir(){
        //     let {data:{success}} = await api.createRootDir("6d796804171a4a88af1d5f1b6adcb54d");
        //     if(success){
        //         alert("success");
        //     }
        // }
       
    }
}