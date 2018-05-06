import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import {Input,Card,Avatar,Tag,Divider} from 'uiw';
import { Button,Modal,Radio } from 'tinper-bee';

import "./index.less";

const UserPlan = (props) =>{
    return (<div>
        <Divider><h3>未完成的计划</h3></Divider>
        <div className="userPlan-card-layout">
        {
            props.on_userPlanS.map(function(userPlan){
                let color="";
                let tag="";
                switch(userPlan.level){
                    case 0:
                    color="red";
                    tag="紧急事件"
                    break;
                    case 1:
                    color="orange";
                    tag="重要事件"
                    break;
                    case 2:
                    color="green";
                    tag="一般事件"
                    break;
                    default:
                    break;

                }
                return(
                <Card title={userPlan.title}  style={{ width: 300 }}>
                <Tag color={color}>{tag}</Tag>
                <Card>
                {userPlan.content}
                </Card>
                
                <Button
                onClick={
                    ()=>{
                        let args={
                            "sessionId":props.sessionId,
                            "userPlan":userPlan,
                            "on_userPlanS":props.on_userPlanS,
                            "end_userPlanS":props.end_userPlanS
                        }
                        actions.home.endPlan(args);
                    }
                }
                >结束计划</Button>

        </Card>
        );
            })
        }
        <Card title="新建标签"  style={{ width: 300 }}>
        <Input placeholder="计划标题"
                value={props.userPlan_title}
                onChange={
                    (e,value)=>{
                        let userPlan_title = value;
                        actions.home.save({userPlan_title});
                    }
                }></Input>
                <Input placeholder="计划内容"
                type="textarea"
                row={3}
                value={props.userPlan_content}
                onChange={
                    (e,value)=>{
                        let userPlan_content = value;
                        actions.home.save({userPlan_content});
                    }
                }></Input>
                <Radio.RadioGroup
                    name="type"
                    selectedValue={props.userPlan_level}
                    onChange={(value)=>{
                        let userPlan_level=value;
                        actions.home.save({userPlan_level});
                    }}>

                    <Radio.RadioButton value="2">一般事件</Radio.RadioButton>

                    <Radio.RadioButton value="1">重要事件</Radio.RadioButton>

                    <Radio.RadioButton  value="0">紧急事件</Radio.RadioButton>

                </Radio.RadioGroup>
                <Button
                onClick={
                    ()=>{
                        let args={
                            "sessionId":props.sessionId,
                            "title":props.userPlan_title,
                            "content":props.userPlan_content,
                            "on_userPlanS":props.on_userPlanS,
                            "level":props.userPlan_level
                        }
                        actions.home.savePlan(args);
                    }
                }
                >保存</Button>

        </Card>
        </div>
        <Divider><h3>已完成的计划</h3></Divider>
        <div className="userPlan-card-layout">
        {
            props.end_userPlanS.map(function(userPlan){
                let color="";
                let tag="";
                switch(userPlan.level){
                    case 0:
                    color="red";
                    tag="紧急事件"
                    break;
                    case 1:
                    color="orange";
                    tag="重要事件"
                    break;
                    case 2:
                    color="green";
                    tag="一般事件"
                    break;
                    default:
                    break;

                }
                return(
                <Card title={userPlan.title}  style={{ width: 300 }}>
                <Tag color={color}>{tag}</Tag>
                <Input placeholder="计划内容"
                type="textarea"
                value={userPlan.content}
                ></Input>
        </Card>
        );
            })
        }
        </div>
        </div>
    );
}
export default UserPlan;