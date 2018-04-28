import {actions} from 'mirrorx';
import * as api from "../services/Photo_Album";
export default {
    name : "photoAlbum",
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