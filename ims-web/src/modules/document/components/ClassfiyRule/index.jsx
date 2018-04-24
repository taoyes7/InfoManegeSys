import React,{ Component } from 'react';
import './index.less';
import { Modal, Button,Collapse,Icon ,Breadcrumb, FormControl} from 'tinper-bee';
import {Tag ,Menu , Dropdown,Card,Divider} from 'uiw';
import ClassfiyRuleModel from "../models/ClassfiyRule"
import mirror, { actions, connect, render } from 'mirrorx';

//注入Model
mirror.model(ClassfiyRuleModel);
    const ClassfiyRule = (props)=>{

    const addRules= ()=>{
        if(props.state.newRuleName!=""){
            let args={
                "sessionId" : props.sessionId,
                "ruleName" :props.state.newRuleName,
                "label" : {
                    "labels":props.state.temp_labels
                },
                "fileType":{
                    "fileTypes":props.state.temp_fileType
                },
                "dir" : props.currentDir
            };
            actions.classfiyRule.addRules(args);
        }else{
            alert("规则名不能为空");
        }
        
    }

    let key_label_list_content_and=0;
    let key_label_list_content_or=0;
    let key_label_list_content_no=0; 
    let key_filetype=0;
    

    const menuRuleAnd = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
    // onClose={() => actions.classfiyRule.onClose()}
    // onSelect={() => actions.classfiyRule.onSelect()}
  >
    <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
      {
          props.state.old_labels.map(function(label){
              if(label.pid!="null"){
                key_label_list_content_and++;
                  return(
                    <Menu.Item index={"2-"+key_label_list_content_and} key={key_label_list_content_and} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
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
                                            "description":label.description,
                                            "color":"red",
                                            "group":"and"
                                        }
                                actions.classfiyRule.addLabelToTempLabels(_label);
                                let args={
                                    "label":_label,
                                    "temp_labels":props.state.temp_labels,
                                    "temp_tags":props.state.temp_tags
                                }
                                actions.classfiyRule.addTotemp_tags(args);
                                }else{
                                        alert("该标签已添加");
                                }
    
                            } 
                        }
                        >{label.content}</Tag>
                        
                    </Menu.Item>
                  );
              }
             
          })
      }
    </Menu.SubMenu>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
    </Menu.SubMenu>
  </Menu>
    );
    const menuRuleOr = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
    // onClose={() => actions.classfiyRule.onClose()}
    // onSelect={() => actions.classfiyRule.onSelect()}
  >
    <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
      {
          props.state.old_labels.map(function(label){
              if(label.pid!="null"){
                key_label_list_content_or++;
                  return(
                    <Menu.Item index={"2-"+key_label_list_content_or} key={key_label_list_content_or} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
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
                                            "description":label.description,
                                            "color":"green",
                                            "group":"or"
                                        }
                                actions.classfiyRule.addLabelToTempLabels(_label);
                                let args={
                                    "label":_label,
                                    "temp_labels":props.state.temp_labels,
                                    "temp_tags":props.state.temp_tags
                                }
                                actions.classfiyRule.addTotemp_tags(args);
                                }else{
                                        alert("该标签已添加");
                                }
    
                            } 
                        }
                        >{label.content}</Tag>
                        
                    </Menu.Item>
                  );
              }
             
          })
      }
    </Menu.SubMenu>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
    </Menu.SubMenu>
  </Menu>
    );
    const menuRuleNo = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
    // onClose={() => actions.classfiyRule.onClose()}
    // onSelect={() => actions.classfiyRule.onSelect()}
  >
    <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
      {
          props.state.old_labels.map(function(label){
              if(label.pid!="null"){
                key_label_list_content_no++;
                  return(
                    <Menu.Item index={"2-"+key_label_list_content_no} key={key_label_list_content_no} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
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
                                            "description":label.description,
                                            "color":"#52575c",
                                            "group":"no"
                                        }
                                actions.classfiyRule.addLabelToTempLabels(_label);
                                let args={
                                    "label":_label,
                                    "temp_labels":props.state.temp_labels,
                                    "temp_tags":props.state.temp_tags
                                }
                                actions.classfiyRule.addTotemp_tags(args);
                                }else{
                                        alert("该标签已添加");
                                }
    
                            } 
                        }
                        >{label.content}</Tag>
                    </Menu.Item>
                  );
              }
             
          })
      }
    </Menu.SubMenu>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
    </Menu.SubMenu>
  </Menu>
    );
    
    const menuFileType = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
  >
      {
          props.state.oldFileType.map(function(fileType){
              
                key_filetype++;
                  return(
                    <Menu.Item index={""+key_filetype} key={key_filetype} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
                         onClick={ ()=>
                            {
                                let isAdd=false;
                                props.state.temp_fileType.map(function(_fileType){
                                    if(fileType.name==_fileType.name){
                                        isAdd=true;
                                    }
                                });
                                if(!(isAdd)){
                                let _fileType={
                                            "name":fileType.name
                                        }
                                actions.classfiyRule.addFiletYpeToTempFileType(_fileType);
                                let args={
                                    "fileType":_fileType,
                                    "temp_fileType":props.state.temp_fileType,
                                    "tags_fileType":props.state.tags_fileType
                                }
                                actions.classfiyRule.addTotags_fileType(args);
                                }else{
                                        alert("该类型已添加");
                                }
    
                            } 
                        }
                        >{fileType.name}</Tag>
                    </Menu.Item>
                  );
              }
          )
      }
  </Menu>
    );
    //change
    const menuRuleAnd_change = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
  >
    <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
      {
          props.state.old_labels.map(function(label){
              if(label.pid!="null"){
                key_label_list_content_and++;
                  return(
                    <Menu.Item index={"2-"+key_label_list_content_and} key={key_label_list_content_and} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
                         onClick={ ()=>
                            {
                                let isAdd=false;
                                props.state.labelGroup.labels.map(function(_label){
                                    if(label.content==_label.content){
                                        isAdd=true;
                                    }
                                });
                                if(!(isAdd)){
                                let _label={
                                            "pid":label.pid,
                                            "content":label.content,
                                            "description":label.description,
                                            "type":label.type,
                                            "color":"red",
                                            "group":"and"
                                        }
                                let args = {
                                    "label":_label,
                                    "sessionId":props.sessionId,
                                    "labelGroup":props.state.labelGroup
                                }
                                        
                                actions.classfiyRule.addSingleLabel(args);
                                }else{
                                        alert("该标签已添加");
                                }
    
                            } 
                        }
                        >{label.content}</Tag>
                        
                    </Menu.Item>
                  );
              }
             
          })
      }
    </Menu.SubMenu>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
    </Menu.SubMenu>
  </Menu>
    );
    const menuRuleOr_change = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
  >
    <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
      {
          props.state.old_labels.map(function(label){
              if(label.pid!="null"){
                key_label_list_content_or++;
                  return(
                    <Menu.Item index={"2-"+key_label_list_content_or} key={key_label_list_content_or} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
                         onClick={ ()=>
                            {
                                let isAdd=false;
                                props.state.labelGroup.labels.map(function(_label){
                                    if(label.content==_label.content){
                                        isAdd=true;
                                    }
                                });
                                if(!(isAdd)){
                                let _label={
                                            "pid":label.pid,
                                            "content":label.content,
                                            "description":label.description,
                                            "type":label.type,
                                            "color":"green",
                                            "group":"or"
                                        }
                                        let args = {
                                            "label":_label,
                                            "sessionId":props.sessionId,
                                            "labelGroup":props.state.labelGroup
                                        }
                                                
                                        actions.classfiyRule.addSingleLabel(args);
                                }else{
                                        alert("该标签已添加");
                                }
    
                            } 
                        }
                        >{label.content}</Tag>
                        
                    </Menu.Item>
                  );
              }
             
          })
      }
    </Menu.SubMenu>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
    </Menu.SubMenu>
  </Menu>
    );
    const menuRuleNo_change = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
  >
    <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
      {
          props.state.old_labels.map(function(label){
              if(label.pid!="null"){
                key_label_list_content_no++;
                  return(
                    <Menu.Item index={"2-"+key_label_list_content_no} key={key_label_list_content_no} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
                         onClick={ ()=>
                            {
                                let isAdd=false;
                                props.state.labelGroup.labels.map(function(_label){
                                    if(label.content==_label.content){
                                        isAdd=true;
                                    }
                                });
                                if(!(isAdd)){
                                let _label={
                                            "pid":label.pid,
                                            "content":label.content,
                                            "description":label.description,
                                            "type":label.type,
                                            "color":"#52575c",
                                            "group":"no"
                                        }
                                        let args = {
                                            "label":_label,
                                            "sessionId":props.sessionId,
                                            "labelGroup":props.state.labelGroup
                                        }
                                                
                                        actions.classfiyRule.addSingleLabel(args);
                                }else{
                                        alert("该标签已添加");
                                }
    
                            } 
                        }
                        >{label.content}</Tag>
                    </Menu.Item>
                  );
              }
             
          })
      }
    </Menu.SubMenu>
    <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
    </Menu.SubMenu>
  </Menu>
    );
    const menuFileType_change = (
        <Menu 
    defaultActive="1"
    defaultOpened={['5']}
    style={{width:240}}
  >
      {
          props.state.oldFileType.map(function(fileType){
              
                key_filetype++;
                  return(
                    <Menu.Item index={""+key_filetype} key={key_filetype} title={<span><Icon type="menu" /><span></span></span>}>
                        <Tag className="tag"  color="#52575c" 
                         onClick={ ()=>
                            {
                                let isAdd=false;
                                props.state.labelGroup.fileTypes.map(function(_fileType){
                                    if(fileType.name==_fileType){
                                        isAdd=true;
                                    }
                                });
                                if(!(isAdd)){
                                let _fileType= fileType.name
                                        
                                let args = {
                                    "fileType":_fileType,
                                    "sessionId":props.sessionId,
                                    "labelGroup":props.state.labelGroup
                                }   
                                actions.classfiyRule.addSingleFileType(args);
                                }else{
                                        alert("该类型已添加");
                                }
    
                            } 
                        }
                        >{fileType.name}</Tag>
                    </Menu.Item>
                  );
              }
          )
      }
  </Menu>
    );
    //

    return (
        <div>
            <Button id="classfiyRule" className="buttom-min" colors="primary" onClick={()=> actions.classfiyRule.openModalAndLoadOldLabels({"sessionId":props.sessionId,"dirId":props.currentDir.pid})}> 管理分类规则</Button>

            <Modal
            show={props.state.Model_classifyRule}
            onHide={()=> actions.classfiyRule.closeModal()}
            style={{width: 450}}
        >
            <Modal.Header className="text-center">
                <Modal.Title>管理分类规则</Modal.Title>
            </Modal.Header>

            <Modal.Body>
            
                <div>管理规则</div>
                {console.log(props.state.labelGroups)}
                {
                    props.state.labelGroups.map(function(labelGroup){
                        
                        return (
                            <div key={labelGroup.priorityLevel}>
                            <Card title={labelGroup.name}  extra={<Icon type="uf-gridcaretarrowup"
                            onClick={()=>{  
                                let args={
                                    "sessionId":props.sessionId,
                                    "curPid":labelGroup.pid,
                                    "classfiyRule":props.state.classfiyRule
                                };
                                actions.classfiyRule.exchangLevel(args);
                            }}
                            ></Icon>} style={{ width: 400 }}>
                               
                               <div className="label-input-layout">
                               {labelGroup.labels.map(function(label){
                                   console.log(props.state.label);
                                   return(
                                       <div>
                                           <Tag className="tag"  color={label.color}  >{label.content}</Tag>
                                        </div>
                                   )
                               })}
                               </div>
                               <div className="label-input-layout">
                               {
                                   labelGroup.fileTypes.map(function(fileType){
                                       return(
                                        <div>
                                            <Tag className="tag"  color="#52575c"  >{fileType}</Tag>
                                        </div>
                                       )
                                   })
                               }
                            </div>
                            <Divider style={{margin:'0px'}}/> 
                            <div className="label-input-layout"> 
                            <Button  shape="border"  onClick={()=> actions.classfiyRule.openChangRulesModel(labelGroup)}>   
                                修改
                            </Button>
                            <Button  shape="border" onClick={()=>{
                               let args = {
                                    "sessionId":props.sessionId,
                                    "dirId":props.currentDir.pid,
                                    "ruleId":labelGroup.pid,
                                    "classfiyId":props.state.classfiyRule.pid
                                };
                                actions.classfiyRule.deleteRules(args);
                            }}>   
                                删除
                            </Button>
                            </div>
                            </Card>
                            </div>
                        )
                    })
                }
                <div onClick={()=> actions.classfiyRule.switchOnCreate()}>新建规则</div>
                <Collapse in={props.state.createClassfiyRule}>
                <div>
                <div  className="label-input-layout">
                        <div className="label-input-tag">规则名称:</div>
                        <FormControl className = "input-size"
                            value={props.state.newRuleName}
                            onChange={(e)=>actions.classfiyRule.newRuleName_onChange(e)}
                            />
                </div>
                <div className="label-input-layout">
                <div className="label-input-tag">
                
                <Dropdown menu={menuFileType}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button shape="border">文件类型:</Button><Icon type="arrow-down" />
                    </a>
                </Dropdown>
                </div>
                {props.state.tags_fileType}
                </div>
                <div>
                    <p>与：&nbsp; {props.state.and}</p>
                    <p>或：&nbsp; {props.state.or}</p>
                    <p>非：&nbsp; {props.state.no}</p>
                </div>
                <Dropdown menu={menuRuleAnd}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button shape="border">添加与标签</Button> <Icon type="arrow-down" />
                    </a>
                </Dropdown>
                <Dropdown menu={menuRuleOr}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button shape="border">添加或标签 </Button><Icon type="arrow-down" />
                    </a>
                </Dropdown>
                <Dropdown menu={menuRuleNo}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button shape="border">添加非标签</Button> <Icon type="arrow-down" />
                    </a>
                </Dropdown>
                <Button shape="border" colors="primary" onClick={()=> addRules()}>添加</Button>
                </div>
                </Collapse>
            </Modal.Body>

            <Modal.Footer className="text-center">
                <Button colors="primary" onClick={()=> actions.classfiyRule.closeModal()}>
                    完成
                </Button>
            </Modal.Footer>
            
        </Modal>

        <Modal
            show = { props.state.showModal_change }
            onHide = { ()=> {
                let showModal_change = false;
                actions.classfiyRule.save({showModal_change});
                }} >
                <Modal.Header>
                    <Modal.Title>{props.state.labelGroup.name}</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                <div className="label-input-layout">
                               {props.state.labelGroup.labels.map(function(label){
                                   console.log(props.state.label);
                                   return(
                                       <div>
                                           <Tag className="tag"  color={label.color} onClick={()=>{
                                               let args ={
                                                    "labelId":label.pid,
                                                    "sessionId":props.sessionId,
                                                    "labelGroup":props.state.labelGroup
                                               }
                                               actions.classfiyRule.deleteLabel(args);
                                           }} >{label.content}</Tag>
                                        </div>
                                   )
                               })}
                               </div>
                               <div className="label-input-layout">
                               {
                                   props.state.labelGroup.fileTypes.map(function(fileType){
                                       return(
                                        <div>
                                            <Tag className="tag"  color="#52575c"  onClick={()=>{
                                               //labelId,sessionId labelGroup
                                               let args ={
                                                    "fileType":fileType,
                                                    "sessionId":props.sessionId,
                                                    "labelGroup":props.state.labelGroup
                                               }
                                               actions.classfiyRule.deleteFileType(args);
                                           }}>{fileType}</Tag>
                                        </div>
                                       )
                                   })
                               }
                               </div>
                               <Divider style={{margin:'0px'}}/>
                               <div className="label-input-layout">
                               <Dropdown menu={menuFileType_change}>
                                    <a className="ant-dropdown-link" href="javascript:;">
                                    <Button shape="border">文件类型:</Button><Icon type="arrow-down" />
                                    </a>
                                </Dropdown>
                                <Dropdown menu={menuRuleAnd_change}>
                                    <a className="ant-dropdown-link" href="javascript:;">
                                    <Button shape="border">添加与标签</Button> <Icon type="arrow-down" />
                                    </a>
                                </Dropdown>
                                <Dropdown menu={menuRuleOr_change}>
                                    <a className="ant-dropdown-link" href="javascript:;">
                                    <Button shape="border">添加或标签 </Button><Icon type="arrow-down" />
                                    </a>
                                </Dropdown>
                                <Dropdown menu={menuRuleNo_change}>
                                    <a className="ant-dropdown-link" href="javascript:;">
                                    <Button shape="border">添加非标签</Button> <Icon type="arrow-down" />
                                    </a>
                                </Dropdown>
                               </div>
                            
                </Modal.Body>

                <Modal.Footer>
                    {/* <Button onClick={ this.close } shape="border" style={{marginRight: 50}}>关闭</Button> */}
                    <Button onClick={()=>{
                        let showModal_change = false;
                        actions.classfiyRule.save({showModal_change});
                    }  } colors="primary">完成</Button>
                </Modal.Footer>
           </Modal>
        </div>
    )
}

export default connect((state) => {//连接组件和状态管理
    return {
        state: state.classfiyRule,
        sessionId:state.login.sessionId,
        currentDir:state.document.currentDir
    }
})(ClassfiyRule)