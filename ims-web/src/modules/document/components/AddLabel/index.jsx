import React,{ Component } from 'react';
import './index.less';
import { Modal, Button,Collapse,Icon ,Breadcrumb, FormControl} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import AddlabelModal from "../models/AddLabel";
import {Tag ,Menu , Dropdown} from 'uiw';

//注入Model
mirror.model(AddlabelModal);
const AddLabel = (props) => {
    
    const closeModalAndSaveLabelsForDir = () =>{
        let args={
            "sessionId":props.sessionId,
            "file":props.currentDir,
            "label":{
                "labels":props.state.temp_labels
            }
        }
        actions.addLabel.closeModalAndSaveLabelsForDir(args);
    }
    const addAndCheckLabel = () =>{
        let args={
            "sessionId":props.sessionId,
            "currentDir":props.currentDir,
            "state":props.state
        }
        actions.addLabel.addAndCheckLabel(args);
    }
        let key_tags=0;
        let key_templabel=0;
        let key_oldLabel=0;
        let key_label_list_type=0;
        let key_label_list_content=0; //
        let key_label_list_color=0;

         const menu_addLabel = (
                <Menu 
            defaultActive="1"
            defaultOpened={['5']}
            style={{width:240}}
            onClose={() => actions.addLabel.onClose()}
            onSelect={() => actions.addLabel.onSelect()}
          >
            <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
            <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
              {
                  props.state.old_labels.map(function(label){
                      if(label.pid!="null"){
                        key_label_list_content++;
                        key_label_list_color=0;
                          return(
                            <Menu.SubMenu index={"2-"+key_label_list_content} key={key_oldLabel} title={<span><Icon type="menu" /><span>
                                <Tag className="tag"  color="#52575c">{label.content}</Tag>
                                </span></span>}>
                                {
                                props.state.tags.map(function(tag){
                                    key_label_list_color++;
                                    if(tag!=null){
                                        return(
                                            <Menu.Item index={"2-"+key_label_list_content+"-"+key_label_list_color}>
                                            <Tag className="tag" key={key_label_list_color} color={tag.color}
                                            onClick={ ()=>
                                                {
                                                    let isAdd=false;
                                                    let isHave=false;
                                                    props.state.temp_labels.map(function(_label){
                                                        if(label.content==_label.content){
                                                            isAdd=true;
                                                        }
                                                    });
                                                    props.currentDir.labels.map(function(_label){
                                                        if(_label!=null){
                                                            if(_label.content==props.state.newLabel_content){
                                                                isHave=true;
                                                            }
                                                        }
                                                        
                                                    });
                                                    if(!(isAdd&&isHave)){
                                                    let _label={
                                                                "pid":label.pid,
                                                                "content":label.content,
                                                                "description":label.description,
                                                                "color":tag.color,
                                                                "level":tag.level,
                                                                "score":tag.score,
                                                                "isAdd":true
                                                            }
                                                    actions.addLabel.addLabelToTempLabels(_label);
                                                    let args={
                                                        "label":_label,
                                                        "temp_labels":props.state.temp_labels,
                                                        "temp_tags":props.state.temp_tags
                                                    }
                                                    actions.addLabel.addTotemp_tags(args);
                                                    }else{
                                                        if(!isExist){
                                                            alert("已拥有该标签");
                                                        }else{
                                                            alert("该标签已添加");
                                                        }
                                                    }
                        
                                                } 
                                            }
                                            >{tag.color}</Tag>
                                            </Menu.Item>
                                        )
                                    }
                                })
                            }
                            </Menu.SubMenu>
                          );
                          key_oldLabel++;
                      }
                     
                  })
              }
            </Menu.SubMenu>
            <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
            </Menu.SubMenu>
          </Menu>
              );

        return(<div>
            <Button className="addLabel" shape="border" onClick={()=>actions.addLabel.openModalAndloadOldLabels({"sessionId":props.sessionId})}>   
                添加标签
            </Button>
             <Modal
                    show={props.state.showModel}
                    onHide={()=>actions.addLabel.closeModalAndResetTempLabels()}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>添加标签</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    <div>
                        {props.state.temp_tags}
                    </div>
                    <div onClick={ ()=> actions.addLabel.switchAddLabel_create()}>创建新标签</div>
                    <Collapse in={props.state.addLabel_create}>
                    <div >
                    <div key={0} className="label-input-layout">
                        <div className="label-input-tag">标签内容:</div>
                        <FormControl className = "input-size"
                            value={props.state.newLabel_content}
                            onChange={(e)=>actions.addLabel.newLabelC_onChange(e)}
                            />
                    </div>
                    <div key={1} className="label-input-layout">
                        <div className="label-input-tag">标签描述:</div>
                        <FormControl className = "input-size"
                        value={props.state.newLabel_describe}
                        onChange={(e)=>actions.addLabel.newLabelD_onChange(e)}
                        />
                    </div>

                    <div className="label-input-layout">
                    <div onClick={ ()=> actions.addLabel.switchAddLabel_create_color()}>
                    <Tag className="tag" color={props.state.newLabel_color} >{props.state.newLabel_color}</Tag>
                    </div>
                    <Collapse  in={props.state.addLabel_create_color}>
                    <div >
                    {
                        props.state.tags.map(function(tag){
                            let args={
                                color:tag.color,
                                level:tag.level,
                                score:tag.score
                            }
                            if(tag!=null){
                                return(
                                    <Tag className="tag" key={key_tags} color={tag.color}
                                    onClick={ ()=> actions.addLabel.setAddTagColor(args)}
                                    >{tag.color}</Tag>
                                )
                                key_tags++;
                            }
                        })
                    }
                    </div>
                    </Collapse>
                    </div>
                    <div className="label-layout-button">
                    <Button className="buttom-min" colors="primary" onClick={()=>addAndCheckLabel()} >
                            添加
                    </Button>
                    <Button className="buttom-min" colors="primary" onClick={()=>actions.addLabel.newLabel_clear()}>
                            清空
                    </Button>
                   
                    </div>
                    </div>
                    </Collapse>
                    
                    <Dropdown menu={menu_addLabel}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <div>添加标签</div> <Icon type="arrow-down" />
                    </a>
                    </Dropdown>
                    </Modal.Body>
                    

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={()=>actions.addLabel.closeModalAndResetTempLabels()}>
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>closeModalAndSaveLabelsForDir()}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>)
    
}

export default connect((state) => {//连接组件和状态管理
    return {
        state: state.addLabel,
        sessionId:state.login.sessionId,
        currentDir:state.document.currentDir
    }
})(AddLabel)