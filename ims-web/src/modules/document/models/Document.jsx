import {actions} from 'mirrorx';
import * as api from '../services/Document'

export default {
    name : "document",
    initialState : {
      fileList:"",
      currentDir:"",
    },
    reducers : {
        save(state, data) {
            return {
                ...state,
                ...data
            }
        },
        img_upload(state){
            alert("img upload");
        }
    },
    effects : {
        async  openRootDir(sessionId){
            let {data,data:{success}} = await api.openRootDir(sessionId);
            
            if(success){
                let {fileResponseDTOArrayList:fileList} = data;
                actions.document.save({fileList});
                actions.document.getCurrentDir(sessionId);
            }else{
                console.log(data.message);
            }
        },
        async getCurrentDir(sessionId){
            let {data:currentDir,data:{success}} = await api.getCurrentDir(sessionId);
            if(success){
                actions.document.save({currentDir});
            }else{
                console.log(data.message);
            }
        },
        async  createDir(args){
                let {data,data:{success}} = await api.createDir(args.sessionId,args.dirName);
                if(success){
                    alert("创建成功");
                }else{
                    console.log(data.message);
                }
        },
        async checkUpLoadFile(args){
                let {data,data:{success}} = await api.checkUpLoadFile(args.sessionId,args.fileName);
                if(success){
                    return data;
                }else{
                    return {
                        "success":false
                    }
                }
        }
    }
}