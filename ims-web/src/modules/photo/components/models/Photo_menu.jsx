import {actions} from 'mirrorx';
import * as api from "../services/Photo_menu";
export default {
    name : "photoMenu",
    initialState : {
        
    },
    reducers : {
        save_1(state, data) {
            return {
                ...state,
                ...data
            }
        }
        
    },
    effects : {
    }
}