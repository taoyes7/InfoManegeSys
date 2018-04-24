import {actions} from 'mirrorx';
import * as api from '../services/Document'

export default {
    name : "document",
    initialState : {
      fileList:[],
      currentDir:[],
      filePathArray:[],
      rulesAndFiles:[],
      key:0
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
        insertToRulesAndFiles(state,data){
            let rulesAndFiles = [];
            let count=0;
            let _count=0;
            rulesAndFiles = data.rulesAndFiles;
            console.log(data);
            rulesAndFiles.map(function(ruleAndfile){
                if(ruleAndfile.labelGroup.pid==data.file.labelGroup.pid){
                    // ruleAndfile.fileResponseDTOArrayList.push(data.file.file);
                    // rulesAndFiles.splice(count,1,ruleAndfile);
                    // break;
                    _count=count;
                }
                count++;
            })
            rulesAndFiles[_count].fileResponseDTOArrayList.push(data.file.file);
            console.log(rulesAndFiles);
            return {
                ...state,
                ...{rulesAndFiles}
            }
        },
        addRulesAndFiles(state,data){
            let rulesAndFiles = [];
            rulesAndFiles = data.rulesAndFiles;
            console.log(data);
            let ruleAndFile =  rulesAndFiles.pop();
            ruleAndFile.fileResponseDTOArrayList.push(data.file);
            rulesAndFiles.push(ruleAndFile);
            console.log(rulesAndFiles);
            return {
                ...state,
                ...{rulesAndFiles}
            }
        },
        refresh(state){
            let rulesAndFiles = [];
            return {
                ...state,
                ...{rulesAndFiles}
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
            let {data:dirFileList,data:{success}} = await api.openRootDir(sessionId);
            
            if(success){
                let {rulesAndFileLIstResponseDTOArrayList:rulesAndFiles}=dirFileList;
                actions.document.save({rulesAndFiles});
                actions.document.getCurrentDir(sessionId);
            }else{
                console.log(data.message);
            }
        },
        async openDir(args){
            let {data:dirFileList,data:{success}} = await api.openDir(args);
            if(success){
                let {rulesAndFileLIstResponseDTOArrayList:rulesAndFiles}=dirFileList;
                actions.document.save({rulesAndFiles});
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
            let {data:dirFileList,data:{success}} = await api.backToParent(sessionId);
            if(success){
                let {rulesAndFileLIstResponseDTOArrayList:rulesAndFiles}=dirFileList;
                actions.document.save({rulesAndFiles});
                actions.document.getCurrentDir_back(sessionId);
            }else{
                console.log(data.message);
            }
        },
        async addTodirFileList(args){
            await actions.document.refresh();
            actions.document.addRulesAndFiles(args);
        },
        async insertTodirFileList(args){
            await actions.document.refresh();
            actions.document.insertToRulesAndFiles(args);
        }
        
        
       
    }
}