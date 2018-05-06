import {actions} from 'mirrorx';
import * as api from "../services/Photo_Album";
export default {
    name : "photoAlbum",
    initialState : {
        api_typecode:"0",
        api_result:"",
        isShow:false,
        plantShow:"",
        animalShow:"",
        foodShow:"",
        carShow:""
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