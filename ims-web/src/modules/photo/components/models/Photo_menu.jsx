import {actions} from 'mirrorx';
import * as api from "../services/Photo_menu";
export default {
    name : "photoMenu",
    initialState : {
        model_upImg:false,
        newImg_descreption:""
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
        async upLoadImgChange(info){  
            if (info.file.status === 'done') {
              
                if(info.file.response.success){
                    // let args={
                    //       "file":info.file.response,
                    //       "rulesAndFiles":props.rulesAndFiles
                    // };
                    // actions.document.insertTodirFileList(args);
                  console.log(`${info.file.name} file uploaded successfully`);
                }else{
                  console.log(`${info.file.name} file upload failed.`);
                  alert(info.file.response.message);
                }
            } else if (info.file.status === 'error') {
              console.log(`${info.file.name} file upload failed.`);
            }
          }
    }
}