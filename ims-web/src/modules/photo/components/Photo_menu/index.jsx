import React from 'react';
import './index.less';
import {Menu,Icon,Input } from 'uiw'
import { FormControl, Modal, Button,Upload} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import Photo_menuModel from "../models/Photo_menu"

mirror.model(Photo_menuModel);
const Photo_menu = (props) => {//组件定义
    const uploadImg_props= {
        name: 'img',
        action: '/photo/upload/img',
        headers: {
          authorization: 'authorization-text'
        },
        data:{
            "sessionId":props.sessionId,
            "description":props.state.newImg_descreption
        }, 
        multiple: true,
        showUploadList: true,
        onChange: (info)=>{actions.photoMenu.upLoadImgChange(info)},
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
          <Menu.Item index="1"><div onClick = {
              ()=>{
                  let model_upImg= true;
                  actions.photoMenu.save({model_upImg});
              }
          }>上传图片</div></Menu.Item>
          <Menu.Item index="2"><div> 新建相册</div></Menu.Item>
          <Menu.Item index="3" ><div>我的相册</div></Menu.Item>
        </Menu>

        <Modal
                    show={props.state.model_upImg}
                    onHide={()=>{
                        let model_upImg = false;
                        actions.photoMenu.save({model_upImg});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>上传新文件</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                        <Upload {...uploadImg_props}>
                            <Button shape="border">
                             Click to Upload
                            </Button>
                        </Upload>
                        <Input 
                            type="textarea" 
                            rows={4} 
                            placeholder="请输入图片描述(可选)"
                            value={props.state.newImg_descreption}
                            onChange={(e,value)=>{
                                let newImg_descreption = value;
                                actions.photoMenu.save({newImg_descreption});
                            }}
                        />
                        
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                        let model_upImg = false;
                        let newImg_descreption = "";
                        actions.photoMenu.save({model_upImg});
                        actions.photoMenu.save({newImg_descreption});
                    }}>
                            完成
                        </Button>
                    </Modal.Footer>
                </Modal>
        </div>
    )
}
export default connect((state) => {//连接组件和状态管理
    return {
        state: state.photoMenu,
        sessionId:state.login.sessionId
    }
})(Photo_menu)