import {actions} from 'mirrorx';
import * as api from "../services/Photo_menu";
export default {
    name : "ablum",
    initialState : {
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
    }
}