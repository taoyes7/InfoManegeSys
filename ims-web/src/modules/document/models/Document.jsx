import {actions} from 'mirrorx';
import * as api from '../services/Document'

export default {
    name : "document",
    initialState : {
      fileList:[],
      currentDir:[],
      filePathArray:[]
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
        },
        addTofileList(state,data){
             let fileList=state.fileList;
            fileList.push(data);
            return {
                ...state,
                ...{fileList}
            }
        },
        addTofilePathArray(state,data){
            let filePathArray = state.filePathArray;
            filePathArray.push(data);
            return {
                ...state,
                ...{filePathArray}
            }
        },
        popFromfilePathArray(state){
            let filePathArray = state.filePathArray;
            filePathArray.pop();
            return {
                ...state,
                ...{filePathArray}
            }
        },
        showState(state,data){
            console.log(state);
            return state;
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
        async openDir(args){
            let {data,data:{success}} = await api.openDir(args);
            if(success){
                let {fileResponseDTOArrayList:fileList} = data;
                actions.document.save({fileList});
                actions.document.getCurrentDir(args.sessionId);
            }else{
                console.log(data.message);
            }
        },
        async getCurrentDir(sessionId){
            let {data:currentDir,data:{success}} = await api.getCurrentDir(sessionId);
            if(success){
                actions.document.save({currentDir});
                actions.document.addTofilePathArray(currentDir);
            }else{
                console.log(data.message);
            }
        },
        async  createDir(args){
                let {data,data:{success}} = await api.createDir(args.sessionId,args.dirName);
                if(success){
                    actions.document.addTofileList(data);
                    actions.document.showState();
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
        },
        async handClickFile(args){
            if(args.data.type=="dir"){
                let _args = {
                    "sessionId":args.sessionId,
                    "dirId":args.data.pid
                }
                actions.document.openDir(_args);
            }else{
                alert("clicked");
            }
        },
        async getCurrentDir_back(sessionId){
            let {data:currentDir,data:{success}} = await api.getCurrentDir(sessionId);
            if(success){
                actions.document.save({currentDir});
                actions.document.popFromfilePathArray();
            }else{
                console.log(data.message);
            }
        },
        async backToParent(sessionId){
            let {data,data:{success}} = await api.backToParent(sessionId);
            if(success){
                let {fileResponseDTOArrayList:fileList} = data;
                actions.document.save({fileList});
                actions.document.getCurrentDir_back(sessionId);
            }else{
                console.log(data.message);
            }
        },
        async addLabelForDir(args){
            let {data:currentDir, data:{success}} = await api.addLabel(args);
            if(success){
                actions.document.save({currentDir});
            }
        },
        async getAllLabels(args){
            let{data,data:{success}} = await api.getAllLabels(args);
            if(success){
                let _labels=[];
                let {labelResponseDTOArrayList:labels} = data;
                _labels=labels;
                return _labels;
            }
        },
        async checkLabel(args){
            let{data:{success,data}} = await api.checkLabel(args);
            if(success){
                return data;
            }
        }
    }
}