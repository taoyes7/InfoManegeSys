import {actions} from 'mirrorx';
import * as api from '../services/AddLabel';
import {Tag  } from 'uiw';


export default {
    name:"classfiyRule",
    initialState : {
        Model_classifyRule:false,
        createClassfiyRule:false,
        showModal_change:false,
        old_labels:[ ],
        temp_tags:{
            and:[],
            or:[],
            no:[]
        },
        and:[],
        or:[],
        no:[],
        temp_labels:[],
        temp_fileType:[],
        tags_fileType:[],
        newRuleName:"",
        oldFileType:[
            {"name":"dir"},
            {"name":"world"},
            {"name":"excel"},
            {"name":"ppt"},
            {"name":"pdf"},
            {"name":"img"}
        ],
        labelGroups:[],
        classfiyRule:"",
        labelGroup:{
            "labels":[],
            "fileTypes":[]

        }
    },
    reducers : {
        openChangRulesModel(state,data){
            let labelGroup = data;
            let showModal_change =true;
            return {
                ...state,
                ...{labelGroup},
                ...{showModal_change}
            }
        },
        newRuleName_onChange(state,e){
            let newRuleName=e;
            return {
                ...state,
                ...{newRuleName}
            }
        },
        save(state, data) {
            return {
                ...state,
                ...data
            }
        },
        openModal(state){
            let Model_classifyRule = true;
            return {
                ...state,
                ...{Model_classifyRule}
            }
        },
        closeModal(state){
            let Model_classifyRule = false;
            return {
                ...state,
                ...{Model_classifyRule}
            }
        },
        switchOnCreate(state){
            let createClassfiyRule = !state.createClassfiyRule;
            return {
                ...state,
                ...{createClassfiyRule}
            }
        },
        addLabelToTempLabels(state,label){
            let temp_labels = state.temp_labels;
            temp_labels.push(label);
            return {
                ...state,
                ...{temp_labels}
            }
        },
        addFiletYpeToTempFileType(state,fileType){
            let temp_fileType = state.temp_fileType;
            temp_fileType.push(fileType);
            return {
                ...state,
                ...{temp_fileType}
            }
        },
        clear(state){
            let temp_tags={
                "and":[],
                "or":[],
                "no":[]
            };
            let and=[];
            let or=[];
            let no=[];
            let temp_labels=[];
            let temp_fileType=[];
            let tags_fileType=[];
            let newRuleName="";
            return {
                ...state,
                ...{temp_tags},
                ...{and},
                ...{or},
                ...{no},
                ...{temp_labels},
                ...{temp_fileType},
                ...{tags_fileType},
                ...{newRuleName}
            }
        }
        
    },
    effects : {
        async openModalAndLoadOldLabels(args){
            // let{data,data:{success}} = await api.getAllLabels(args);
            // if(success){
                // await actions.classfiyRule.loadFileRules(args);
                // let {labelResponseDTOArrayList:old_labels} = data;
                // actions.classfiyRule.save({old_labels});
                actions.classfiyRule.openModal();
            // }else{
            //     actions.classfiyRule.openModal();
            // }
        },
        async loadFileRules(args){
            let {data:classfiyRule,data:{success}} = await api.getFileRules(args);
            if(success){
                let {labelGroups:labelGroups} = classfiyRule;
                actions.classfiyRule.save({labelGroups});
                actions.classfiyRule.save({classfiyRule});

            }
        },
        async addTotags_fileType(args){
            const tag = <Tag  className="tag" color="#52575c" 
            onClick={()=>actions.classfiyRule.ClickForFileType(args)} 
            >{args.fileType.name}</Tag>
            let tags_fileType = args.tags_fileType;
            tags_fileType.push(tag);
            actions.classfiyRule.save({tags_fileType});
        },
        async addTotemp_tags(args){
            
            const tag = <Tag  className="tag" color={args.label.color} 
                   onClick={()=>actions.classfiyRule.ClickForTempTags(args)} 
                   >{args.label.content}</Tag>
            let temp_tags =args.temp_tags;
            switch(args.label.group){
                case "and":
                    temp_tags.and.push(tag);
                    let and = temp_tags.and;
                    actions.classfiyRule.save({and})
                    break;
                case "or":
                    temp_tags.or.push(tag);
                    let or = temp_tags.or;
                    actions.classfiyRule.save({or})
                    break;
                case "no":
                    temp_tags.no.push(tag);
                    let no = temp_tags.no;
                    actions.classfiyRule.save({no})
                    break;
                default:
                    break;
            }
            actions.classfiyRule.save({temp_tags});
        },
        async ClickForFileType(args){
            let count=0;
            let _count=0;
            let temp_fileType = args.temp_fileType;
            let tags_fileType = args.tags_fileType;
            temp_fileType.map(function(fileType){
                if(fileType==args.fileType){
                    _count=count;
                }
                count++;
            });
            temp_fileType.splice(_count,1);
            tags_fileType.splice(_count,1);
            actions.classfiyRule.save({temp_fileType});
            actions.classfiyRule.save({tags_fileType});

        },
        async ClickForTempTags(args){
            let count=0;
            let _count=0;
            let count_tag=0;
            let _count_tag=0;
            let temp_tags = args.temp_tags;
            let temp_labels =args.temp_labels; 
            temp_labels.map(function(_label){
                if(_label==args.label){
                    _count=count;
                    _count_tag=count_tag;
                }
                if(_label.group==args.label.group){
                    count_tag++;
                }
                count++;
            });
            temp_labels.splice(_count,1);
            switch(args.label.group){
                case "and":
                    temp_tags.and.splice(_count_tag,1);
                    let and = temp_tags.and;
                    actions.classfiyRule.save({and})
                    break;
                case "or":
                    temp_tags.or.splice(_count_tag,1);
                    let or = temp_tags.or;
                    actions.classfiyRule.save({or})
                    break;
                case "no":
                    temp_tags.no.splice(_count_tag,1);
                    let no = temp_tags.no;
                    actions.classfiyRule.save({no})
                    break;
                default:
                    break;
            }
            actions.addLabel.save({temp_labels});
            actions.addLabel.save({temp_tags});
       
        },
        async addRules(args){
            
            let {data:currentDir, data:{success}} = await api.addRules(args);
            if(success){
                let _args={
                    "sessionId":args.sessionId,
                    "dirId":args.dir.pid
                }
                await actions.classfiyRule.loadFileRules(_args);
                actions.document.refreshfile(_args);
                actions.document.save({currentDir});
                actions.classfiyRule.clear();
            }
        },
        async deleteRules(args){
            let { data:{success}} = await api.deleteRules(args);
            if(success){
                await actions.classfiyRule.loadFileRules(args);
                actions.document.refreshfile(args);
            }
        },
        async deleteLabel(args){
            let {data:{success}} = await api.deleteLabel(args);
            if(success){
                let count=0;
                let _count=0;
                let labelGroup = args.labelGroup;
                labelGroup.labels.map(function(label){
                    if(args.labelId==label.pid){
                        _count = count;
                    }
                    count++;
                });
                labelGroup.labels.splice(_count,1);
                actions.classfiyRule.save({labelGroup});
                
            }
        },
        async deleteFileType(args){
            //sessionId, fileType,labeGroup
            let {data:{success}} = await api.deleteFileType(args);
            if(success){
                let count=0;
                let _count=0;
                let labelGroup = args.labelGroup;
                labelGroup.fileTypes.map(function(fileType){
                    if(args.fileType==fileType){
                        _count = count;
                    }
                    count++;
                });
                labelGroup.fileTypes.splice(_count,1);
                actions.classfiyRule.save({labelGroup});
                
            }
        },
        async addSingleLabel(args){
            let {data:{success}} = await api.addSingleLabel(args);
            if(success){
                let labelGroup = args.labelGroup;
                labelGroup.labels.push(args.label);
                actions.classfiyRule.save({labelGroup});
                
            }
        },
        async addSingleFileType(args){
            let {data:{success}} = await api.addSingleFileType(args);
            if(success){
                let labelGroup = args.labelGroup;
                labelGroup.fileTypes.push(args.fileType);
                actions.classfiyRule.save({labelGroup});
                
            }
        },
        async exchangLevel(args){
            let curPid=args.curPid;
            let count=0;
            let _count=0;
            let labelGroups = args.classfiyRule.labelGroups;
            labelGroups.map(function(labeGroup){
                if(labeGroup.pid==curPid){
                    _count=count;
                }
                count++;
            });
            console.log(labelGroups[_count-1].pid);
            let nextPid=labelGroups[_count-1].pid;
            let cur = labelGroups[_count];
            let next = labelGroups[_count-1];
            labelGroups.splice(_count-1,2,cur,next);
            
            let _args={
                "sessionId":args.sessionId,
                "curPid":curPid,
                "nextPid":nextPid,
                "classfiyRuleId":args.classfiyRule.pid
            }
            let {data:{success}} = await api.exchangLevel(_args);
            if(success){
                actions.classfiyRule.save(labelGroups);
                actions.document.refreshfile(args);
            }

            
        }


    }
        
}