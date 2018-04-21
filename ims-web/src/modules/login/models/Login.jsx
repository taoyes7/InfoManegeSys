import {actions} from 'mirrorx';
import * as api from "../services/Login";
export default {
    name : "login",
    initialState : {
       sessionId:"6d796804171a4a88af1d5f1b6adcb54d",
       state:true
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
        async register(){
             let  {data:success} = await api.register();
            //  debugger;
             alert(success);
        },
        async login(){
            let {data:{state,sessionId}} = await api.login();

            if(state==true){
                actions.login.save({state});
                actions.login.save({sessionId});
                actions.routing.push({
                    pathname: `/home`
                });
            }
        },
        async createRootDir(){
            let {data:{success}} = await api.createRootDir("6d796804171a4a88af1d5f1b6adcb54d");
            if(success){
                alert("success");
            }
        }
       
    }
}