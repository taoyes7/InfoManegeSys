import {actions} from 'mirrorx';
import * as api from "../services/Photo_Album";
export default {
    name : "photoAlbum",
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