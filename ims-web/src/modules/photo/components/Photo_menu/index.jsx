import React from 'react';
import './index.less';
import {Menu,Icon} from 'uiw'
import { FormControl, Modal, Button} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import Photo_menuModel from "../models/Photo_menu"

mirror.model(Photo_menuModel);
const Photo_menu = (props) => {//组件定义
    const uploadDoc_props= {
        name: 'photo',
        action: '/photo/upLoad/img',
        headers: {
          authorization: 'authorization-text'
        },
        data:{
            "sessionId":props.sessionId
        }, 
        multiple: true,
        showUploadList: true,
        onChange: (info)=>actions.photoMenu.upLoadImgChange(info),
        // accept:"",
        beforeUpload(file){
            return true;
        },
        
      }; 
    
    return (
        <div className={props.className}>
            <Menu
          defaultActive="1"
          theme={'dark'}
          onSelect={(index)=>actions.docMenu.onSelect(index)}
        //   className="menu-size"
        >
          <Menu.Item index="1"><div>上传图片</div></Menu.Item>
          <Menu.Item index="2"><div> 新建相册</div></Menu.Item>
          <Menu.Item index="3" ><div>我的相册</div></Menu.Item>
        </Menu>
        </div>
    )
}
export default connect((state) => {//连接组件和状态管理
    return {
        state: state.photoMenu,
        sessionId:state.login.sessionId
    }
})(Photo_menu)