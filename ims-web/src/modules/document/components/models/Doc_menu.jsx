import {actions} from 'mirrorx';
import * as api from '../services/Doc_menu';


export default {
    name:"docMenu",
    initialState : {
        model_upfile:false,
        model_labelType_new:false,
        model_label_newLabel:false,
        model_label_change:false,
        model_label_changetype:false,
        model_delete_LabelType:false,
        delete_LabelType:"",
        newLabelType_describe:"",
        newLabelType_name:"",
        newLabel_name:"",
        newLabel_describe:"",
        labelTypes:[],
        labelGroups:[],
        abandon_labelGroup:{
            "labelType":{
                "name":"废弃标签"
            },
            "labels":[]
        },
        change_label:{
            "success": true,
            "content": "数据结构",
            "discription": "初始化",
            "type": {"pid":"8b4650d6445547f188fe9ca08e710628","name":"计算机类"},
            "level": 0,
            "color": "red",
            "score": 0,
            "pid": "554f0660094d44d68bb5c17d71735759"
        },
        change_label_type:{
            "description" : "计算机相关",
            "name" : "计算机类",
            "pid" : "8b4650d6445547f188fe9ca08e710628",
            "success" : true
        },
        new_label_type:""
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
                    let model_labelType_new = false;
                    let newLabelType_describe="";
                    let newLabelType_name="";
                    let labelTypes =[];
                    let labelGroups = [];
                    labelGroups = args.labelGroups;
                    labelTypes = args.labelTypes;
                    let labelGroup=labelGroups.pop();
                    labelGroups.push({
                        "labelType":data,
                        "labels":[]
                    })
                    labelGroups.push(labelGroup);
                    let labelType_no = labelTypes.pop();
                    let labelType_else = labelTypes.pop();
                    labelTypes.push(data);
                    labelTypes.push(labelType_else);
                    labelTypes.push(labelType_no);
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
                    let {labelGroups,abandon_labelGroup} = data;
                    console.log(labelGroups);
                    actions.docMenu.save({labelGroups});
                    actions.docMenu.save({abandon_labelGroup});
                }
            },
            async changeLabelType(args){
                let {data:{success}} = await api.changeLabelType(args);
                if(success){    
                    let labelGroups = [];
                    let change_label = [];
                    let type = args.type;
                    labelGroups = args.labelGroups;
                    change_label = args.change_label;
                    let count=0;
                    let _count=0;
                    let _count_0=0;
                    let _count_1=0;
                    labelGroups.map(function(labelGroup){
                        if(labelGroup.labelType.name==change_label.type.name){
                            _count=count;
                        }
                        if(labelGroup.labelType.name==type.name){
                            _count_0=count;
                        }
                        count++;
                    })
                    count=0;
                    
                    if(change_label.type.name=="废弃标签"){
                        let abandon_labelGroup = args.abandon_labelGroup;
                        abandon_labelGroup.labels.map(function(label){
                            if(label.content==change_label.content){
                                _count_1 = count;
                            }
                            count++;
                        })
                        abandon_labelGroup.labels.splice(_count_1,1);
                    }else{
                        labelGroups[_count].labels.map(function(label){
                            if(label.content==change_label.content){
                                _count_1 = count;
                            }
                            count++;
                        })
                        labelGroups[_count].labels.splice(_count_1,1);
                    }
                    
                    change_label.type=type;
                    labelGroups[_count_0].labels.push(change_label);
                    actions.docMenu.save({change_label});
                    actions.docMenu.save({labelGroups});
                    
                    let model_label_changetype = false;
                    actions.docMenu.save({model_label_changetype});
                }
            },
            async deleteLabel(args){
                let {data:{success}} = await api.deleteLabel(args);
                if(success){
                    let labelGroups = [];
                    let change_label = [];
                    labelGroups = args.labelGroups;
                    change_label = args.change_label;
                    let count=0;
                    let _count=0;
                    let _count_1=0;
                    labelGroups.map(function(labelGroup){
                        if(labelGroup.labelType.name==change_label.type.name){
                            _count=count;
                        }
                        count++;
                    })
                    count=0;
                    labelGroups[_count].labels.map(function(label){
                        if(label.content==change_label.content){
                            _count_1 = count;
                        }
                        count++;
                    })
                    labelGroups[_count].labels.splice(_count_1,1);
                    actions.docMenu.save({labelGroups});
                    let model_label_change = false;
                    actions.docMenu.save({model_label_change});
                    let abandon_labelGroup = args.abandon_labelGroup;

                    change_label.type= abandon_labelGroup.labelType
                    actions.docMenu.save({change_label});

                   abandon_labelGroup.labels.push(change_label);
                   actions.docMenu.save({abandon_labelGroup});
                }
            },
            async newLabel(args){
                let {data,data:{success}}= await api.newLabel(args);
                if(success){
                    let labelGroups = args.labelGroups;
                    let count=0;
                    let _count=0;
                    labelGroups.map(function(labelGroup){
                        if(labelGroup.labelType.name==data.type.name){
                            _count = count;
                        }
                        count++;
                    })

                    labelGroups[_count].labels.push(data);
                    actions.docMenu.save(labelGroups);

                    let new_label_type="";
                    let newLabel_name="";
                    let newLabel_describe="";
                    let model_label_newLabel=false;
                    actions.docMenu.save({new_label_type});
                    actions.docMenu.save({newLabel_name});
                    actions.docMenu.save({newLabel_describe});
                    actions.docMenu.save({model_label_newLabel});
                }else{
                    alert(data.message);
                }
            },
            async deleteLabelType(args){
                let {data,data:{success}} = await api.deleteLabelType(args);
                if(success){
                 let labelTypes=[];
                 let labelGroups=[];
                 labelTypes = args.labelTypes;
                 labelGroups = args.labelGroups;
                 let delete_LabelType = args.delete_LabelType;
                 let count=0;
                 let _count=0;
                 labelTypes.map(function(labelType){
                     if(labelType.name==delete_LabelType.name){
                         _count=count;
                     }
                     count++;
                 });
                 labelTypes.splice(_count,1);
                 labelGroups.splice(_count,1);
                 actions.docMenu.save({labelTypes});
                 actions.docMenu.save({labelGroups});
                 let abandon_labelGroup = data;
                 actions.docMenu.save({abandon_labelGroup});
                 let model_delete_LabelType = false;
                 actions.docMenu.save({model_delete_LabelType});
                 
                }
            }

    }
        
}