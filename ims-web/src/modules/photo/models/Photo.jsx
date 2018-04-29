import {actions} from 'mirrorx';
import * as api from "../services/Photo";
export default {
    name : "photo",
    initialState : {
        
    },
    reducers : {
        save_1(state, data) {
            console.log(state);
            console.log(data);
            return {
                ...state,
                ...data
            }
        }
        
    },
    effects : {
       
    }
}