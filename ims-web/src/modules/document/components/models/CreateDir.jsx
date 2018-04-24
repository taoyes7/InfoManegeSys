import {actions} from 'mirrorx';
import * as api from '../services/CreateDir'

export default {
    name : "createdir",
    initialState : {
            showModal: false,
            newDir_name: "",
    },
    reducers : {
        newDir_close_cancel(state) {
            console.log("cancel");
            let showModal= false;
            return {
                ...state,
                ...{showModal}
            }
        },
        newDir_close_ok(state) {
            console.log("ok");
            let showModal= false;
            return {
                ...state,
                ...{showModal}
            }
        },
        newDir_onChange(state,e) {
            let newDir_name = e;
            return {
                ...state,
                ...{newDir_name}
            }
        },
        newDir_open(state) {
            let showModal = true
            return {
                ...state,
                ...{showModal}
            }
        }
    },
    effects : {
        async  createDir(args){
            console.log(args);
            if(args.newDir_name!=""){
                actions.createdir.newDir_close_ok();
                let {data,data:{success}} = await api.createDir(args.sessionId,args.newDir_name);
                if(success){
                    console.log(data);
                    let _args={
                        "rulesAndFiles":args.rulesAndFiles,
                        "file":data
                    }
                    actions.document.addTodirFileList(_args);
                }else{
                    console.log(data.message);
                }
            }else{
                alert("文件名不能为空");
            }
    }
    }
}