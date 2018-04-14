import {actions} from 'mirrorx';
import * as api from "../services/Login";
export default {
    name : "login",
    initialState : {
       sessionId:"aaeea59796a447da88557e93adb674a3",
       state:true
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
        }
    }
}