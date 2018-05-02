import React from 'react';
import './index.less';
import {Menu,Icon,Input ,Dropdown,Tag} from 'uiw'
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

    let key_label_type=0;
    let key_label_label=0;
    const menu_ablue_Labels = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
  >

    {
        props.labelGroups.map(function(labelGroup){
            key_label_type++;
            key_label_label=0;
            return (
            <Menu.SubMenu index={key_label_type} key={key_label_type} title={<span><Icon type="star-on" />{labelGroup.labelType.name}<span></span></span>}>
            {
                labelGroup.labels.map(function(label){
                    key_label_label++;
                return(
                    <Menu.Item  index={key_label_type+"-"+key_label_label} key={key_label_type+"-"+key_label_label} title={<span><Icon type="menu" /><span>
                    {label.content}
                    </span></span>}>
                                <div 
                                onClick={ ()=>
                                    {
                                        let isAdd=false;
                                        props.state.temp_labels.map(function(_label){
                                            if(label.content==_label.content){
                                                isAdd=true;
                                            }
                                        });
                                        if(!(isAdd)){
                                        let _label={
                                                    "pid":label.pid,
                                                    "content":label.content,
                                                    "description":label.description
                                                }
                                        let temp_labels=props.state.temp_labels;
                                        temp_labels.push(_label);
                                        actions.photoMenu.save({temp_labels});
                                        }else{
                                            alert("该标签已添加");
                                        }
            
                                    } 
                                }
                                >{label.content}</div>
                </Menu.Item> );
                })
            }
            </Menu.SubMenu>
            )
        })
    }

  </Menu>
      );
    
    return (
        <div className={props.className}>
        {props.currentAblum.name!="root"?<div><h4>{props.currentAblum.name}</h4>
        
        <Button onClick={
            ()=>{
                if(props.currentAblum.name!="未分类"){
                    let args={
                        "sessionId":props.sessionId,
                        "ablumId":props.currentAblum.pid
                      }
                    actions.photo.getParentAblum(args).then(
                        (result)=>{
                            if(result.success){
                                let args={
                                    "sessionId":props.sessionId,
                                    "ablumId":result.pid
                                  }
                                  actions.photo.openAblum(args).then((result)=>{
                                    if(result){
                                      actions.photo.getCurrentAblum(args);
                                      actions.photo.getAblumData(args);
                                    }
                                  });
                            }
                        }
                    )
                }else{
                    let args={
                        "sessionId":props.sessionId,
                        "ablumId":props.rootAblum.pid
                      }
                      actions.photo.openAblum(args).then((result)=>{
                        if(result){
                          actions.photo.getCurrentAblum(args);
                          actions.photo.getAblumData(args);
                        }
                      });
                }
            }
        }>返回</Button>
        </div>:""}
        
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
          <Menu.Item index="2"><div
          onClick={
              ()=>{
                let model_newAlbum = true;
                actions.photoMenu.save({model_newAlbum});
              }
          }
          > 新建相册</div></Menu.Item>
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
                <Modal
                    show={props.state.model_newAlbum}
                    onHide={()=>{
                        let model_newAlbum = false;
                        actions.photoMenu.save({model_newAlbum});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>新建相册</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                       <Input type="text"
                       placeholder="输入相册名称(必填)"
                       value={props.state.newAblum_name}
                       onChange={(e,value)=>{
                        let newAblum_name = value;
                        actions.photoMenu.save({newAblum_name});
                    }}>
                       </Input>
                        <Input 
                            type="textarea" 
                            rows={4} 
                            placeholder="请输入相册描述(可选)"
                            value={props.state.newAlbum_descreption}
                            onChange={(e,value)=>{
                                let newAlbum_descreption = value;
                                actions.photoMenu.save({newAlbum_descreption});
                            }}
                        />
                    <div>
                    <Dropdown menu={menu_ablue_Labels}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button shape="border">添加标签</Button> <Icon type="arrow-down" />
                    </a>
                    </Dropdown>
                    {props.state.temp_labels.map(function(label){
                        return(
                            <Tag color="#52575c"
                            onClick={
                                ()=>{
                                    let count=0;
                                    let _count=0;
                                    let temp_labels = props.state.temp_labels;
                                    temp_labels.map(function(_label){
                                        if(_label.content==label.content){
                                            _count=count;
                                        }
                                        count++;
                                    });
                                    temp_labels.splice(_count,1);
                                    actions.photoMenu.save(temp_labels);
                                }
                            }
                            >{label.content}</Tag>
                        )
                    })
                    }
                    </div>
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                    <Button bordered style={{ marginRight: 20 }} onClick={()=>{
                       let model_newAlbum = false;
                       actions.photoMenu.save({model_newAlbum});
                    }} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                            
                            if(props.state.newAblum_name!=""){
                                let args={
                                    "label" : {
                                        "labels":props.state.temp_labels,
                                    },
                                    "sessionId":props.sessionId,
                                    "name":props.state.newAblum_name,
                                    "description":props.state.newAlbum_descreption
                                }
                                actions.photoMenu.createAblum(args).then((result)=>{
                                    if(result.success){
                                        let model_newAlbum = false;
                                        let newAlbum_descreption = "";
                                        let temp_labels=[];
                                        actions.photoMenu.save({model_newAlbum});
                                        actions.photoMenu.save({newAlbum_descreption});
                                        actions.photoMenu.save({temp_labels});
                                        
                                        let {ablums:ablumS,photos:photoS} = result;
                                        actions.photo.save({ablumS});
                                        actions.photo.save({photoS});
                                    }
                                })
                            }else{
                                alert("相册名不能为空");
                            }
                        
                    }}>
                            创建
                        </Button>
                    </Modal.Footer>
                </Modal>
        </div>
    )
}
export default connect((state) => {//连接组件和状态管理
    return {
        state: state.photoMenu,
        sessionId:state.login.sessionId,
        labelGroups:state.docMenu.labelGroups,
        ablumS:state.photo.ablumS,
        currentAblum:state.photo.currentAblum,
        rootAblum:state.photo.rootAblum
    }
})(Photo_menu)