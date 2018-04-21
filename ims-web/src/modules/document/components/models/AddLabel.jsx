import {actions} from 'mirrorx';
import * as api from '../services/AddLabel'
import {Tag  } from 'uiw';


export default {
    name : "addLabel",
    initialState : {
            showModel: false,
            addLabel_create:true,
            addLabel_add:false,
            addLabel_create_color:false,
            newDir_name: "",
            newLabel_content:"",
            newLabel_describe:"",
            newLabel_color:"red",
            newLabel_level:1,
            newLabel_score:0.9,
            temp_labels:[],
            old_labels:[
                {"pid":"null"}
            ],
            tags:[
                {"color":"red" , "level":1, "score":0.9},
                {"color":"orange" , "level":2, "score":0.7},
                {"color":"yellow" , "level":3, "score":0.5},
                {"color":"green" , "level":4, "score":0}
            ],
            color_tags:[],
            temp_tags:[]
    },
    reducers : {
        switchAddLabel_create(state){
            let addLabel_create = !state.addLabel_create;
            return {
                ...state,
                ...{addLabel_create}
            }
        },
        switchAddLabel_create_color(state){
            let addLabel_create_color = !state.addLabel_create_color;
            return {
                ...state,
                ...{addLabel_create_color}
            }
        },
        setAddTagColor(state,data){

            let newLabel_color = data.color;
            let newLabel_level = data.level;
            let newLabel_score = data.score;
            let addLabel_create_color =  !state.addLabel_create_color
            return {
                ...state,
                ...{newLabel_color},
                ...{newLabel_level},
                ...{newLabel_score},
                ...{addLabel_create_color}
            }
            
        },
        addLabelToTempLabels(state,label){
            let temp_labels = state.temp_labels;
            temp_labels.push(label);
            return {
                ...state,
                ...temp_labels
            }
        },
        openModelAndOldLabel(state,data){
            let showModel =true;
            let old_labels = data;
            return {
                ...state,
                ...{showModel},
                ...{old_labels}
            }
        
        },
        closeModalAndResetTempLabels(state){
            
            let showModel = false;
            let temp_labels = [];
            return {
                ...state,
                ...{showModel},
                ...{temp_labels}
            }
        },
        newLabelC_onChange(state,e){
            let newLabel_content = e;
            return {
                ...state,
                ...{newLabel_content}
            }
        },
        newLabelD_onChange(state,e){
            let newLabel_describe = e;
            return {
                ...state,
                ...{newLabel_describe}
            }
        },
        newLabel_clear(state){
            let newLabel_describe = "";
            let newLabel_content = "";
            return {
                ...state,
                ...{newLabel_describe},
                ...{newLabel_content}
            }
        },
        save(state, data) {
            return {
                ...state,
                ...data
            }
        },
        newLabel_add(state,data){
            let temp_labels = data;
            let newLabel_content="";
            let newLabel_describe="";
            let newLabel_color="red";
            let newLabel_level=1;
            let newLabel_score=0.9;
            return {
                ...state,
                ...{temp_labels},
                ...{newLabel_content},
                ...{newLabel_describe},
                ...{newLabel_color},
                ...{newLabel_level},
                ...{newLabel_score}
            }
        },
        onSelect() {},
        onClose(index) {
        console.log("index::",index)
        }
    },
    effects : {
        async mapForTempLabels(temp_labels){
            let key_label=0;
            if(temp_labels!=[]){
                console.log(temp_labels);
                let result=[];
                temp_labels.map(function(label){
                        result.push(<Tag className="tag" key={key_label} color={label.color} >{label.color}</Tag>);
                    key_label++;
                });
            }else{
            }
        },
        async openModalAndloadOldLabels(args){
            let{data,data:{success}} = await api.getAllLabels(args);
            if(success){
                let {labelResponseDTOArrayList:labels} = data;
                actions.addLabel.openModelAndOldLabel(labels);
            }else{
                actions.addLabel.openModelAndOldLabel([]);
            }
        },
        async closeModalAndSaveLabelsForDir(args){
            let {data:currentDir, data:{success}} = await api.addLabel(args);
            if(success){
                actions.document.save({currentDir});
            }
            actions.addLabel.closeModalAndResetTempLabels();
        },
        async addAndCheckLabel(args){
            let isAdd=false;
            let isHave=false;
            let isExist=false;
            let state=args.state;
            if(state.temp_labels!=null){
                state.temp_labels.map(function(label){
                    if(label.content==state.newLabel_content){
                        isAdd=true;
                    }
                })
            }
            if(args.currentDir.labels!=null){
                args.currentDir.labels.map(function(label){
                    if(label!=null){
                        if(label.content==state.newLabel_content){
                            isHave=true;
                        }
                    }
                    
                });
            }
            
            if(!(isAdd||isHave)){
                let _args={
                    "sessionId":args.sessionId,
                    "label":state.newLabel_content
                } 
                let{data:{success,data}} = await api.checkLabel(_args);
                isExist = data;
                if(isExist){
                    let label={
                        "content":state.newLabel_content,
                        "description":state.newLabel_describe,
                        "color":state.newLabel_color,
                        "level":state.newLabel_level,
                        "score":state.newLabel_score,
                        "isAdd":false
                    }
                    let labels = state.temp_labels;
                    labels.push(label);
                    let _args2={
                        "label":label,
                        "temp_labels":state.temp_labels,
                        "temp_tags":state.temp_tags
                    }
                    actions.addLabel.addTotemp_tags(_args2);
                    actions.addLabel.newLabel_add(labels);
                    
                }else{
                    alert("该标签已存在请直接添加");
                }
                
            }else{
                if(!isExist){
                    alert("已拥有该标签");
                }else{
                    alert("该标签已添加");
                }
            }
        },
        async addTotemp_tags(args){
            
            const tag = <Tag  className="tag" color={args.label.color} 
                   onClick={()=>actions.addLabel.ClickForTempTags(args)} 
                   >{args.label.content}</Tag>
            let temp_tags =args.temp_tags;
            temp_tags.push(tag);
            actions.addLabel.save({temp_tags});
        },
        async ClickForTempTags(args){
                let count=0;
                let _count=0;
                let temp_tags = args.temp_tags;
                let temp_labels =args.temp_labels; 
                temp_labels.map(function(_label){
                    if(_label==args.label){
                        _count=count;
                    }
                    count++;
                });
                temp_labels.splice(_count,1);
                temp_tags.splice(_count,1);
                actions.addLabel.save({temp_labels});
                actions.addLabel.save({temp_tags});
           
        }
    }
}


// this.state.temp_labels.map(function(label){
//     if(label!=null){
//        return(<Tag key={key_templabel} className="tag" color={label.color} 
//        onClick={()=>{
//             let _labels=shif.state.temp_labels;
//             let count=0;
//             let _count=0;
//             _labels.map(function(_label){
//                 if(_label==label){
//                     _count=count;
//                 }
//                 count++;
//             });
//             _labels.splice(_count,1);
//             shif.setState({
//                 temp_labels:_labels
//             }
//         );
//         console.log(shif.state.temp_labels);
//        }} 
//        >{label.content}</Tag>) 
//        key_templabel++;
//     }
// })


