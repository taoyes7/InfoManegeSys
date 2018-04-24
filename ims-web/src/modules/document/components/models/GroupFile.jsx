import {actions} from 'mirrorx';
import * as api from '../services/GroupFile';


export default {
    name:"groupFile",
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
        // async getClassfiyedFileGroups(args){
        //     let {data:dirFileList,data:{success}} = await api.getClassfiyedFileGroups(args);
        //     if(success){
        //         actions.groupFile.save({dirFileList});
        //     }
        // }

    }
        
}