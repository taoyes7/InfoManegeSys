import {actions} from 'mirrorx';
import * as api from '../services/Document'

export default {
    name : "doc_dir",
    initialState : {
            
            showModelAddLabel: false,
            addLabel_create:true,
            addLabel_add:false,
            addLabel_create_color:false,
           
            newLabel_content:"",
            newLabel_describe:"",
            newLabel_color:"red",
            newLabel_level:1,
            newLabel_score:0.9,
            temp_labels:[],
            old_labels:[],
            tags:[
                {"color":"red" , "level":1, "score":0.9},
                {"color":"orange" , "level":2, "score":0.7},
                {"color":"yellow" , "level":3, "score":0.5},
                {"color":"green" , "level":4, "score":0}
            ]
    },
    reducers : {
    },
    effects : {
    }
}