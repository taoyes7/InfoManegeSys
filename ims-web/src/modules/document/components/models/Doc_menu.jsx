import {actions} from 'mirrorx';
import * as api from '../services/Doc_menu';


export default {
    name:"docMenu",
    initialState : {
        model_upfile:false,
        model_labelType_new:false,
        newLabelType_describe:"",
        newLabelType_name:"",
        labelTypes:[],
        labelGroups:[]
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
            async onSelect(index) {
                switch(index){
                    case "1":
                        let model_upfile = true;
                        actions.docMenu.save({model_upfile});
                        break;
                    case "3-2-1":
                        let model_labelType_new = true;
                        actions.docMenu.save({model_labelType_new});
                        break;
                    default:
                    break;
              }
            },
            async newLabelType(args){
                let {data,data:{success}} = await api.newLabelType(args);
                if(success){
                    console.log(data);
                    let model_labelType_new = false;
                    let newLabelType_describe="";
                    let newLabelType_name="";
                    let labelTypes =[];
                    let labelGroups = [];
                    labelGroups = args.labelGroups();
                    labelTypes = args.labelTypes;
                    labelGroups.push({
                        "labelType":data,
                        "labels":[]
                    })
                    labelTypes.push(data);
                    actions.docMenu.save({model_labelType_new});
                    actions.docMenu.save({newLabelType_describe});
                    actions.docMenu.save({newLabelType_name});
                    actions.docMenu.save({labelTypes});
                    actions.docMenu.save({labelGroups});
                }else{
                    alert(data.message);
                }
            },
            async getAllLabelType(args){
                let {data,data:{success}} = await api.getAllLabelType(args);
                if(success){
                    let {labelTypes} = data;
                    actions.docMenu.save({labelTypes});
                }
            },
            async getAllLabelsByGroup(args){
                let {data,data:{success}} = await api.getAllLabelsByGroup(args);
                if(success){
                    let {labelGroups} = data;
                    actions.docMenu.save({labelGroups});
                }
            }

    }
        
}