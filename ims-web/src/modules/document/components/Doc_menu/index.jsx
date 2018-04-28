import React,{ Component } from 'react';
import './index.less';
import {Tag,Avatar,Divider,Dropdown ,Menu } from 'uiw';
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
                return true;
            },
            
          }; 
          let key_labelType=0;
          let key_label_labelType=0;
          let key_label_label=0;
          let key_labelType_change=0

          const menu_changeLabelType=(
            <Menu
            defaultActive="1"
            theme={'dark'}
            className="menu-size"
          >
          {
                  props.state.labelTypes.map(function(labelType){
                    key_labelType_change++;
                      return (
                        <Menu.Item index={"1-"+key_labelType_change} key={key_labelType_change}><div
                        onClick={()=>{
                            if(labelType.name==props.state.change_label.type.name){
                                alert("不能选择标签所属类型")
                            }else{
                                let model_label_changetype = true;
                                let change_label_type = labelType;
                                actions.docMenu.save({model_label_changetype});
                                actions.docMenu.save({change_label_type});
                            }
                            
                        }}
                        >{labelType.name}</div></Menu.Item>
                      )
                  })
              }
          </Menu>
          );
          const menu_newLabel_LabelType=(
            <Menu
            defaultActive="1"
            theme={'dark'}
            className="menu-size"
          >
          {
                  props.state.labelTypes.map(function(labelType){
                    key_labelType_change++;
                      return (
                        <Menu.Item index={"1-"+key_labelType_change} key={key_labelType_change}><div
                        onClick={()=>{
                                let new_label_type = labelType;
                                actions.docMenu.save({new_label_type});
                        }}
                        >{labelType.name}</div></Menu.Item>
                      )
                  })
              }
          </Menu>
          )
          
        return (<div id="doc_menu" > 
        <FreeScrollBar>
            <center><Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' ,marginTop: '10%'}} size="large">涛</Avatar></center>
            
            <Menu
          defaultActive="1"
          theme={'light'}
          onSelect={(index)=>actions.docMenu.onSelect(index)}
          className="menu-size"
        >
          <Menu.Item index="1">上传新文件</Menu.Item>
          <Menu.Item index="2" ><div onClick={()=>{console.log(props.state.labelGroups)}}>我的文件夹</div></Menu.Item>
          <Menu.SubMenu index="3" title={<span><span>标签管理</span></span>}>
            <Menu.ItemGroup title="标签">
              <Menu.Item index="3-1-1"><div
              onClick={()=>{
                let model_label_newLabel = true;
                actions.docMenu.save({model_label_newLabel});
            }}
              >新建标签</div></Menu.Item>
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
                                    <Menu.Item index={"3-2-2-"+key_labelType+"-"+key_label_label} key={"3-2-2-"+key_labelType+"-"+key_label_label}>
                                    <div onClick={()=>{
                                        let change_label= label;
                                        let model_label_change =true;
                                        actions.docMenu.save({change_label});
                                        actions.docMenu.save({model_label_change});
                                    }} >{label.content}</div>
                                    </Menu.Item>
                                )
                            })
                        }
                        </Menu.SubMenu>
                      )
                  })
              }
              
              <Menu.SubMenu index={"3-2-2-"+(++key_label_labelType)}  title={<span><Icon type="star-on" /><span>{ props.state.abandon_labelGroup.labelType.name}</span></span>}>
                        {
                            props.state.abandon_labelGroup.labels.map(function(label){
                                key_label_label++;
                                return(
                                    <Menu.Item index={"3-2-2-"+key_labelType+"-"+key_label_label} key={"3-2-2-"+key_labelType+"-"+key_label_label}>
                                    <div onClick={()=>{
                                        let change_label= label;
                                        let model_label_change =true;
                                        actions.docMenu.save({change_label});
                                        actions.docMenu.save({model_label_change});
                                    }} >{label.content}</div>
                                    </Menu.Item>
                                )
                            })
                        }
                        </Menu.SubMenu>
              </Menu.SubMenu>
            </Menu.ItemGroup>
            <Menu.ItemGroup title="标签类型">
              <Menu.Item index="3-2-1">新建类型</Menu.Item>
              <Menu.SubMenu index="3-2-2" title={<span><Icon type="star-on" /><span>管理类型</span></span>}>
              {
                  props.state.labelTypes.map(function(labelType){
                    key_labelType++;
                      return (
                        <Menu.Item index={"3-2-2-"+key_labelType} key={key_labelType}><div
                        onClick={()=>{
                            if(labelType.name=="未分组标签"){
                                alert("该分组不可删除");
                            }else{
                                let model_delete_LabelType = true;
                                let delete_LabelType = labelType;
                                actions.docMenu.save({delete_LabelType});
                                actions.docMenu.save({model_delete_LabelType});
                            }
                         }}
                        >{labelType.name}</div></Menu.Item>
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
                        if(props.state.newLabelType_name==""){
                            alert("类型名不能为空");
                        }else{
                            let args={
                                "sessionId":props.sessionId,
                                "name":props.state.newLabelType_name,
                                "description":props.state.newLabelType_describe,
                                "labelTypes":props.state.labelTypes,
                                "labelGroups":props.state.labelGroups
                            }
                            actions.docMenu.newLabelType(args);
                        }
                    }}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Modal
                    show={props.state.model_label_change}
                    onHide={()=>{
                        let model_label_change = false;
                        actions.docMenu.save({model_label_change});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title><Tag color="#52575c" >{props.state.change_label.content}</Tag></Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    
                    <div  className="label-input-layout">
                        <div className="label-input-tag">所属类型:</div>
                        <div>{props.state.change_label.type.name}</div>
                    </div>
                    <div  className="label-input-layout">
                    <Dropdown menu={menu_changeLabelType}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button shape="border">修改标签类型</Button> <Icon type="arrow-down" />
                    </a>
                    </Dropdown>
                    {props.state.change_label.type.name=="废弃标签"? "" :<Button className="back" shape="border" color="red" onClick={()=>{
                        let args={
                            "sessionId":props.sessionId,
                            "labelGroups":props.state.labelGroups,
                            "change_label":props.state.change_label,
                            "abandon_labelGroup":props.state.abandon_labelGroup
                        };
                        actions.docMenu.deleteLabel(args);
                    }}>   
                        删除标签
                    </Button>}
                    </div>

                        
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        
                        <Button colors="primary" onClick={()=>{
                            let model_label_change = false;
                            actions.docMenu.save({model_label_change});
                    }}>
                            完成
                        </Button>
                    </Modal.Footer>
                </Modal>
                <Modal
                    show={props.state.model_label_changetype}
                    onHide={()=>{
                        let model_label_change = false;
                        actions.docMenu.save({model_label_changetype});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Body>
                    
                    <div  className="label-input-layout">
                        <div className="label-input-tag">类型名称:</div>
                        <div>{props.state.change_label_type.name}</div>
                    </div>
                    <div  className="label-input-layout">
                        <div className="label-input-tag">类型描述:</div>
                        <div>{props.state.change_label_type.description}</div>
                    </div>
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                    <Button bordered style={{ marginRight: 20 }} onClick={()=>{
                       let model_label_changetype = false;
                       actions.docMenu.save({model_label_changetype});
                    }} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                            let args={
                                "sessionId":props.sessionId,
                                "type":props.state.change_label_type,
                                "labelGroups":props.state.labelGroups,
                                "change_label":props.state.change_label,
                                "abandon_labelGroup":props.state.abandon_labelGroup
                            }
                            actions.docMenu.changeLabelType(args);
                    }}>
                            确认修改
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Modal
                    show={props.state.model_label_newLabel}
                    onHide={()=>{
                        let model_label_newLabel = false;
                        actions.docMenu.save({model_label_newLabel});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Body>
                    
                    <div  className="label-input-layout">
                        <div className="label-input-tag">标签名称:</div>
                        <FormControl className = "input-size"
                            value={props.state.newLabel_name}
                            onChange={(e)=>{
                                let newLabel_name= e
                                actions.docMenu.save({newLabel_name})}
                            }
                            />
                    </div>
                    <div  className="label-input-layout">
                        <div className="label-input-tag">标签描述:</div>
                        <FormControl className = "input-size"
                        value={props.state.newLabel_describe}
                        onChange={(e)=>{
                            let newLabel_describe= e
                            actions.docMenu.save({newLabel_describe})}
                        }
                        />
                    </div>
                    <Dropdown menu={menu_newLabel_LabelType}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button shape="border">选择分组</Button> <Icon type="arrow-down" />
                    </a>
                    </Dropdown>
                    {props.state.new_label_type==""? null:<Tag color="#52575c">{props.state.new_label_type.name}</Tag>} 
                    </Modal.Body>
                        
                    <Modal.Footer className="text-center">
                    <Button bordered style={{ marginRight: 20 }} onClick={()=>{
                       let model_label_newLabel = false;
                       actions.docMenu.save({model_label_newLabel});
                    }} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                            if(props.state.newLabel_name==""){
                                alert("标签名不能为空")
                            }else if(props.state.new_label_type==""){
                                alert("标签类型不能为空")
                            }else{
                                let args={  
                                    "sessionId":props.sessionId,
                                    "newLabel_name":props.state.newLabel_name,
                                    "newLabel_describe":props.state.newLabel_describe,
                                    "new_label_type":props.state.new_label_type,
                                    "labelGroups":props.state.labelGroups
                                }
                                actions.docMenu.newLabel(args);
                            }
                            
                    }}>
                            创建
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Modal
                    show={props.state.model_delete_LabelType}
                    onHide={()=>{
                        let model_delete_LabelType = false;
                        actions.docMenu.save({model_delete_LabelType});
                    }}
                    style={{width: 450}}
                >
                 <Modal.Header className="text-center">
                        <Modal.Title>确认删除</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    
                     </Modal.Body>
                        
                    <Modal.Footer className="text-center">
                    <Button bordered style={{ marginRight: 20 }} onClick={()=>{
                       let model_delete_LabelType = false;
                       actions.docMenu.save({model_delete_LabelType});
                    }} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                                let args={  
                                    "sessionId":props.sessionId,
                                    "delete_LabelType":props.state.delete_LabelType,
                                    "labelTypes":props.state.labelTypes,
                                    "labelGroups":props.state.labelGroups
                                }
                                actions.docMenu.deleteLabelType(args);
                            
                            
                    }}>
                            确定删除
                        </Button>
                    </Modal.Footer>
                </Modal>
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