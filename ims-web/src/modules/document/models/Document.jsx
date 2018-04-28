import {actions} from 'mirrorx';
import * as api from '../services/Document'

export default {
    name : "document",
    initialState : {
      fileList:[],
      currentDir:[],
      filePathArray:[],
      rulesAndFiles:[],
      select_labelGroup:{
          "name":null
      },
      select_fileList:[],
      key:0
    },
    reducers : {
        clearSelect(state){
            let select_labelGroup={
                "name":null
            };
            let select_fileList =[];
            return {
                ...state,
                ...{select_labelGroup},
                ...{select_fileList}
            }
        },
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
                actions.docMenu.getAllLabelType({"sessionId":sessionId});
                actions.docMenu.getAllLabelsByGroup({"sessionId":sessionId});
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
        async refreshfile(args){
            let {data:dirFileList,data:{success}} = await api.openDir(args);
            if(success){
                let rulesAndFiles=[];
                actions.document.save({rulesAndFiles});
                let {rulesAndFileLIstResponseDTOArrayList:_rulesAndFiles}=dirFileList;
                rulesAndFiles = _rulesAndFiles;
                actions.document.save({rulesAndFiles});
            }else{
                console.log(data.message);
            }
        }
        ,
        async getCurrentDir(sessionId){
            let {data:currentDir,data:{success}} = await api.getCurrentDir(sessionId);
            if(success){
                await actions.document.save({currentDir});
                await actions.document.addTofilePathArray(currentDir);
                let args ={
                    "sessionId":sessionId,
                    "dirId":currentDir.pid
                }
                actions.classfiyRule.loadFileRules(args);
                actions.select.loadFileTypesAndLabels(args);
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
        async handClickDir(args){
                let _args = {
                    "sessionId":args.sessionId,
                    "dirId":args.data.pid
                }
                actions.document.openDir(_args);
        },
        async getCurrentDir_back(sessionId){
            let {data:currentDir,data:{success}} = await api.getCurrentDir(sessionId);
            if(success){
                await actions.document.save({currentDir});
                await actions.document.popFromfilePathArray();
                let args ={
                    "sessionId":sessionId,
                    "dirId":currentDir.pid
                }
                actions.classfiyRule.loadFileRules(args);
                actions.select.loadFileTypesAndLabels(args);
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
        },
        async addLabelToFile (args){
            let {data,data:{success}} = await api.addLabelToFile(args);
            if(success){
                return success;
            }else{
                alert(data.message)
            }

        },
        async deleteLabelFromFile(args){
            let {data,data:{success}} = await api.deleteLabelFromFile(args);
            if(success){
                return success;
            }else{
                alert(data.message)
            }
        },
        async downLoadFile(args){
            let {data,data:{success}} = await api.downLoadFile(args);
            console.log(data);
            return data;
            // if(success){
            //     return success;
            // }else{
            //     alert(data.message)
            // }
        },
        async deleteFile(args){
            let  {data:{success}} = await api.deleteFile(args);
            if(success){
                actions.document.getRulesAndFiles(args);
                return success;
            }
        },
        async getRulesAndFiles(args){
            let {data,data:{success}} = await api.getRulesAndFiles(args);
            if(success){
                let {rulesAndFileLIstResponseDTOArrayList:rulesAndFiles}=data;
                actions.document.save({rulesAndFiles});
            }
        }
    }
}