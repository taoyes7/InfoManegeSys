import {actions} from 'mirrorx';
import * as api from "../services/Photo";
export default {
    name : "photo",
    initialState : {
        rootAblum:"",
        currentAblum:{
            "name":""
        },
        ablumS:[],
        photoS:[],
        weiFenLei_ablum:""
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
        async getRootAblum(args){
            let {data,data:{success}} = await api.getRootAblum(args);
            if(success){
                let rootAblum = data;
                actions.photo.save({rootAblum});
                return success;
            }
        },
        async openAblum(args){
            let {data,data:{success}} = await api.openAblum(args);
            if(success){
                return success;
            }
        },
        async getCurrentAblum(args){
            let {data,data:{success}} = await api.getCurrentAblum(args);
            if(success){
                let currentAblum = data;
                actions.photo.save({currentAblum});
                return success;
            }
        },
        async getAblumData(args){
            let {data,data:{success}} = await api.getAblumData(args);
            if(success){
                let {ablums:ablumS,photos:photoS} = data;
                actions.photo.save({ablumS});
                actions.photo.save({photoS});
                return success;
            }
        },
        async justGetAblumData(args){
            let {data,data:{success}} = await api.getAblumData(args);
            if(success){
                // let {ablums:ablumS,photos:photoS} = data;
                // actions.photo.save({ablumS});
                // actions.photo.save({photoS});
                return data;
            }
        },
        async getUnClassfiyedAblum(args){
            let {data,data:{success}} = await api.getUnClassfiyedAblum(args);
            if(success){
                let weiFenLei_ablum= data;
                actions.photo.save({weiFenLei_ablum});
                return success;
            } 
        },
        async getParentAblum(args){
            let {data,data:{success}} = await api.getParentAblum(args);
            if(success){
                return data;
            }
        },
        async changeAblumImg(args){
            let {data,data:{success}} = await api.changeAblumImg(args);
            if(success){
                let {ablums:ablumS} = data;
                actions.photo.save({ablumS});
                return success;
            }
        },
        async addNewLabelToAblum(args){
            let {data:{success}} = await api.addNewLabelToAblum(args);
            if(success){
                return success;
            }
        },
        async removeLabelFromAblum(args){
            let {data:{success}} = await api.removeLabelFromAblum(args);
            if(success){
                return success;
            }
        },
        async addNewLabelToPhoto(args){
            let {data:{success}} = await api.addNewLabelToPhoto(args);
            if(success){
                return success;
            }
        },
        async removeLabelFromPhoto(args){
            let {data:{success}} = await api.removeLabelFromPhoto   (args);
            if(success){
                return success;
            }
        },
        async deleteAblum(args){
            let {data,data:{success}} = await api.deleteAblum(args);
            if(success){
                let {ablums:ablumS} = data;
                actions.photo.save({ablumS});
                return success;
            }
        },
        async deletePhoto(args){
            let {data,data:{success}} = await api.deletePhoto(args);
            if(success){
                let {photos:photoS} = data;
                actions.photo.save({photoS});
                return success;
            }
        }
    }
}