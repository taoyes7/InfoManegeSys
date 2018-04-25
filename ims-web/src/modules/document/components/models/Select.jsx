import {actions} from 'mirrorx';
import * as api from '../services/Select';


export default {
    name:"select",
    initialState : {
        fileTypes:[],
        labels:[],
        select_labels:[],
        select_fileTypes:[],
        select_color:"green"
    },
    reducers : {
        save(state, data) {
            return {
                ...state,
                ...data
            }
        },
        handleChange(state,data){
            let select_color = data;
            return {
                ...state,
                ...{select_color}
            }
        },
        clickLabel(state,data){
            let isAdd=false;
            let select_labels = state.select_labels;
            select_labels.map(function(label){
                if(label.content==data.content){
                    isAdd=true;
                }
            })
            if(!isAdd){
                select_labels.push(data);
            }else{
                alert("该标签已添加");
            }
            return {
                ...state,
                ...{select_labels}
            }
        },
        clickFileType(state,data){
            let isAdd = false;
            let select_fileTypes = state.select_fileTypes;
            select_fileTypes.map(function(fileType){
                if(fileType==data){
                    isAdd=true;
                }
            })
            if(!isAdd){
                select_fileTypes.push(data);
            }else{
                alert("该类型已添加");
            }
            return {
                ...state,
                ...{select_fileTypes}
            }
        },
        clickSelected_label(state,data){
            let select_labels = state.select_labels;
            let count=0;
            let _count=0;
            select_labels.map(function(label){
                if(label.content==data){
                    _count=count;
                }
                count++;
            });
            select_labels.splice(_count,1);
            return {
                ...state,
                ...{select_labels}
            }
        },
        clickSelected_fileType(state,data){
            let select_fileTypes = state.select_fileTypes;
            let count=0;
            let _count=0;
            select_fileTypes.map(function(fileType){
                if(fileType==data){
                    _count=count;
                }
                count++;
            });
            select_fileTypes.splice(_count,1);
            return {
                ...state,
                ...{select_fileTypes}
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
        async loadFileTypesAndLabels(args){
            let {data,data:{success}} = await api.loadFileTypesAndLabels(args);
            if(success){
                let {fileTypes,labels} = data;
                actions.select.save({fileTypes});
                actions.select.save({labels});
            }
        },
        async select(args){
            let {data,data:{success}} = await api.select(args);
            if(success){
                let {labelGroup:select_labelGroup,fileResponseDTOArrayList:select_fileList} = data;
                console.log(data);
                actions.document.save({select_labelGroup});
                actions.document.save({select_fileList});
            }
        }
    }
        
}