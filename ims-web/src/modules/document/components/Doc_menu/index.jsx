import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider ,Menu ,Icon as uiw_Icon} from 'uiw';
import { Modal, FormControl, Label, FormGroup, Button, Upload,Icon} from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import Doc_menuModel from "../models/Doc_menu";
import FreeScrollBar from 'react-free-scrollbar';
//注入Model
mirror.model(Doc_menuModel);

const Doc_menu = (props) => {//组件定义
    
   const handleUpLoadSingleFileChange=(info)=> {  
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          
            if(info.file.response.success){
                let args={
                      "file":info.file.response,
                      "rulesAndFiles":props.rulesAndFiles
                };
                console.log(args);
                actions.document.insertTodirFileList(args);
              console.log(`${info.file.name} file uploaded successfully`);
            }else{
              console.log(`${info.file.name} file upload failed.`);
              console.log(info.file.response);
              alert(info.file.response.message);
            }
          
        } else if (info.file.status === 'error') {
          console.log(`${info.file.name} file upload failed.`);
        }
      }
        const uploadDoc_props= {
            name: 'file1',
            action: '/doc/upLoadSingleFile',
            headers: {
              authorization: 'authorization-text'
            },
            data:{
                "sessionId":props.sessionId
            }, 
            multiple: true,
            showUploadList: true,
            onChange: (info)=>handleUpLoadSingleFileChange(info),
            accept:"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            beforeUpload(file){
                // let args={
                //     "sessionId":this.props.sessionId,
                //     "fileName":file.name
                // }
                // let data=actions.document.checkUpLoadFile(args);
                // let{success} = data;
                // if(success){
                //     return true;
                // }else{
                //     return false;
                // }
                return true;
            },
            
          }; 
          let key_labelType=0;
          let key_label_labelType=0;
          let key_label_label=0;
        return (<div id="doc_menu" > 
        <FreeScrollBar>
            <center><Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' ,marginTop: '10%'}} size="large">涛</Avatar></center>
            {/* <div id="doc_menu_upload" onClick={this.open}><center>上传新文件</center></div> */}
            {/* <div id="doc_menu_myDoc"><center>我的文件夹</center></div> */}
            <Menu
          defaultActive="1"
          theme={'light'}
          onSelect={(index)=>actions.docMenu.onSelect(index)}
          className="menu-size"
        >
          <Menu.Item index="1"><uiw_Icon type="date" />上传新文件</Menu.Item>
          <Menu.Item index="2" ><uiw_Icon type="menu" /><div onClick={()=>{console.log(props.state.labelGroups)}}>我的文件夹</div></Menu.Item>
          <Menu.SubMenu index="3" title={<span><Icon type="star-on" /><span>标签管理</span></span>}>
            <Menu.ItemGroup title="标签">
              <Menu.Item index="3-1-1">新建标签</Menu.Item>
              <Menu.SubMenu index="3-1-2" title={<span><Icon type="star-on" /><span>管理标签</span></span>}>
              {
                  props.state.labelGroups.map(function(labelGroup){
                    key_label_labelType++;
                    key_label_label=0;
                      return (
                        <Menu.SubMenu index={"3-2-2-"+key_label_labelType} key={"3-2-2-"+key_label_labelType} title={<span><Icon type="star-on" /><span>{labelGroup.labelType.name}</span></span>}>
                        {
                            labelGroup.labels.map(function(label){
                                key_label_label++;
                                return(
                                    <Menu.Item index={"3-2-2-"+key_labelType+"-"+key_label_label} key={"3-2-2-"+key_labelType+"-"+key_label_label}>{label.content}</Menu.Item>
                                )
                            })
                        }
                        </Menu.SubMenu>
                      )
                  })
              }
              </Menu.SubMenu>
            </Menu.ItemGroup>
            <Menu.ItemGroup title="标签类型">
              <Menu.Item index="3-2-1">新建类型</Menu.Item>
              <Menu.SubMenu index="3-2-2" title={<span><Icon type="star-on" /><span>管理类型</span></span>}>
              {
                  props.state.labelTypes.map(function(labelType){
                    key_labelType++;
                      return (
                        <Menu.Item index={"3-2-2-"+key_labelType} key={key_labelType}>{labelType.name}</Menu.Item>
                      )
                  })
              }
              </Menu.SubMenu>
            </Menu.ItemGroup>
            </Menu.SubMenu>
        </Menu>
            <Modal
                    show={props.state.model_upfile}
                    onHide={()=>{
                        let model_upfile = false;
                        actions.docMenu.save({model_upfile});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>上传新文件</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                        <Upload {...uploadDoc_props}>
                            <Button shape="border">
                            <Icon type="uf-upload" /> Click to Upload
                            </Button>
                        </Upload>
                        
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={()=>{
                        let model_upfile = false;
                        actions.docMenu.save({model_upfile});
                    }} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                        let model_upfile = false;
                        actions.docMenu.save({model_upfile});
                    }}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>

                 <Modal
                    show={props.state.model_labelType_new}
                    onHide={()=>{
                        let model_labelType_new = false;
                        actions.docMenu.save({model_labelType_new});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>新建标签类型</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    <div  className="label-input-layout">
                        <div className="label-input-tag">类型名称:</div>
                        <FormControl className = "input-size"
                            value={props.state.newLabelType_name}
                            onChange={(e)=>{
                                let newLabelType_name= e
                                actions.docMenu.save({newLabelType_name})}
                            }
                            />
                    </div>
                    <div  className="label-input-layout">
                        <div className="label-input-tag">类型描述:</div>
                        <FormControl className = "input-size"
                        value={props.state.newLabelType_describe}
                        onChange={(e)=>{
                            let newLabelType_describe= e
                            actions.docMenu.save({newLabelType_describe})}
                        }
                        />
                    </div>
                        
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={()=>{
                        let model_labelType_new = false;
                        actions.docMenu.save({model_labelType_new});
                    }} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                        
                        let args={
                            "sessionId":props.sessionId,
                            "name":props.state.newLabelType_name,
                            "description":props.state.newLabelType_describe,
                            "labelTypes":props.state.labelTypes,
                            "labelGroups":props.state.labelGroups
                        }
                        actions.docMenu.newLabelType(args);
                    }}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>

                {/* <Modal
                    show={props.state.model_labelType_new}
                    onHide={()=>{
                        let model_labelType_new = false;
                        actions.docMenu.save({model_labelType_new});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>新建标签类型</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    <div  className="label-input-layout">
                        <div className="label-input-tag">类型名称:</div>
                        <FormControl className = "input-size"
                            value={props.state.newLabelType_name}
                            onChange={(e)=>{
                                let newLabelType_name= e
                                actions.docMenu.save({newLabelType_name})}
                            }
                            />
                    </div>
                    <div  className="label-input-layout">
                        <div className="label-input-tag">类型描述:</div>
                        <FormControl className = "input-size"
                        value={props.state.newLabelType_describe}
                        onChange={(e)=>{
                            let newLabelType_describe= e
                            actions.docMenu.save({newLabelType_describe})}
                        }
                        />
                    </div>
                        
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={()=>{
                        let model_labelType_new = false;
                        actions.docMenu.save({model_labelType_new});
                    }} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                        
                        let args={
                            "sessionId":props.sessionId,
                            "name":props.state.newLabelType_name,
                            "description":props.state.newLabelType_describe,
                            "labelTypes":props.state.labelTypes
                        }
                        actions.docMenu.newLabelType(args);
                    }}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal> */}
            </FreeScrollBar>
        </div>)
      
}

export default connect((state) => {//连接组件和状态管理
    return {
        state: state.docMenu,
        sessionId:state.login.sessionId,
        rulesAndFiles:state.document.rulesAndFiles
    }
})(Doc_menu)