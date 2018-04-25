import React from 'react';
import './index.less';
import { FormControl, Modal, Button,Collapse, Icon,Radio} from 'tinper-bee';
import {Tag ,Card,Divider} from 'uiw';
import mirror, { actions, connect, render } from 'mirrorx';
import SelectModel from "../models/Select";
import img_search from './search.png';

//注入Model
mirror.model(SelectModel);

const Select = (props) => {//组件定义   
    let key_label=0;
    let key_fileType=0;
    let key_select_label=0;
    let key_select_fileType=0;
    return (
        <div>
            <Card className="card-size" title="筛选"  
                extra={
                    <div>
                <Radio.RadioGroup 
                    name="rules"
                    selectedValue={props.state.select_color}
                    onChange={(value)=>actions.select.handleChange(value)}>

                    <Radio.RadioButton value="red">与</Radio.RadioButton>

                    <Radio.RadioButton value="green">或</Radio.RadioButton>

                    <Radio.RadioButton  value="#52575c">非</Radio.RadioButton>

                </Radio.RadioGroup>
                <img className="icon-size" src={img_search} onClick={()=>{
                    let args={
                        "sessionId":props.sessionId,
                        "dirId":props.currentDir.pid,
                        "label":{
                            "labels":props.state.select_labels
                        },
                        "fileType":{
                            "fileTypes":props.state.select_fileTypes
                        }
                    }
                    actions.select.select(args);
                } }></img>
                </div>
                } >
            {
                props.state.select_labels.map(function(label){
                    key_select_label++;
                    return(<Tag className="tag-set" key={key_select_label} color={label.color}
                    onClick={()=>{
                        actions.select.clickSelected_label(label.content);
                    }}
                    >{label.content}</Tag>);
                })
            }
            {
                props.state.select_fileTypes.map(function(fileType){
                    key_select_fileType++;
                    return(<Tag className="tag-set" key={key_select_fileType} color="#52575c"
                    onClick={()=>{
                        actions.select.clickSelected_fileType(fileType);
                    }}
                    >{fileType}</Tag>)
                })
            }
            </Card>
            <Card className="card-size" title="文件标签"   >
                
                {
                    props.state.labels.map(function(label){
                        key_label++;
                        return(<Tag className="tag-set" key={key_label} color="#52575c"
                        onClick={()=>{
                            let args={
                                "pid":label.pid,
                                "content":label.content,
                                "color":props.state.select_color
                            };
                            actions.select.clickLabel(args);
                        }}
                        >{label.content}</Tag>);
                    })
                }
            </Card>
            <Card className="card-size" title="文件类型"   >
            {
                props.state.fileTypes.map(function(fileType){
                    key_fileType++;
                    return(<Tag className="tag-set" key={key_fileType} color="#52575c"
                    onClick={()=>{
                        actions.select.clickFileType(fileType);
                    }}
                    >{fileType}</Tag>);
                })
            }
            </Card>
        </div>
    )
}

export default connect((state) => {//连接组件和状态管理
    return {
        state: state.select,
        sessionId:state.login.sessionId,
        currentDir:state.document.currentDir
    }
})(Select)