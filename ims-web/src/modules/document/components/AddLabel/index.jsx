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

        let key_label_type=0;
        let key_label_label=0;
        let key_label_color=0;

         const menu_addLabel = (
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
                            key_label_color=0;
                        return(
                            <Menu.SubMenu  index={key_label_type+"-"+key_label_label} key={key_label_type+"-"+key_label_label} title={<span><Icon type="menu" /><span>
                            {label.content}
                            </span></span>}>
                            {
                            props.state.tags.map(function(tag){
                                key_label_color++;
                                if(tag!=null){
                                    return(
                                        <Menu.Item index={key_label_type+"-"+key_label_label+"-"+key_label_color} key={key_label_type+"-"+key_label_label+"-"+key_label_color}>
                                        <Tag className="tag"  color={tag.color}
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
                                                        if(_label.content==label.content){
                                                            isHave=true;
                                                        }
                                                    }
                                                    
                                                });
                                                if(!(isAdd||isHave)){
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
                                                    if(isHave){
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
                        </Menu.SubMenu> );
                        })
                    }
                    </Menu.SubMenu>
                    )
                })
            }

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
                    <div  className="label-input-layout">
                        <div className="label-input-tag">标签内容:</div>
                        <FormControl className = "input-size"
                            value={props.state.newLabel_content}
                            onChange={(e)=>actions.addLabel.newLabelC_onChange(e)}
                            />
                    </div>
                    <div  className="label-input-layout">
                        <div className="label-input-tag">标签描述:</div>
                        <FormControl className = "input-size"
                        value={props.state.newLabel_describe}
                        onChange={(e)=>actions.addLabel.newLabelD_onChange(e)}
                        />
                    </div>

                    <div  className="label-input-layout">
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
                                key_tags++;
                                return(
                                    <Tag className="tag" key={key_tags} color={tag.color}
                                    onClick={ ()=> actions.addLabel.setAddTagColor(args)}
                                    >{tag.color}</Tag>
                                )
                               
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
        currentDir:state.document.currentDir,
        labelGroups:state.docMenu.labelGroups
    }
})(AddLabel)