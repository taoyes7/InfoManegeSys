import {actions} from 'mirrorx';
import * as api from "../services/Home";
export default {
    name : "home",
    initialState : {
        Img : [
            require('E:/Download/photo01.jpg'),
            require('E:/Download/photo02.jpg'),
            require('E:/Download/photo03.jpg'),
        ],
        src: require('E:/Download/img_head.jpg'),
        album_src: require('E:/Download/img_head.jpg'),
        info_text01:[
        {"name":"姓名","value":"张雪"},
        {"name":"职业","value":"中学教师"},
        {"name":"从业时间","value":"两年"}
        ],
        motto:[{"name":"签名","value":"最好的生活, 最美的自己"}],
        info_text02:[
        {"name":"电话","value":"18010541632"},
        {"name":"QQ","value":"1982038972"},
        {"name":"E-Mail","value":"1982038972@qq.com"},
        {"name":"地址","value":"四川大学(江安校区)"}
        ]
    },
    reducers : {
        // save_1(state, data) {
        //     return {
        //         ...state,
        //         ...data
        //     }
        // }
        
    },
    effects : {
        async  doc_click(){
            let  {data:{data,success}} = await api.CallApi("ni hao");
            alert(data+success);
        }
    }
}