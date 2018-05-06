import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import {Input,Card,Avatar} from 'uiw';
import { Button,Modal } from 'tinper-bee';

import "./index.less";

const UserLink = (props) =>{
    return (<div className="userlink-layout">
        {
            props.userLinkS.map(function(userLink){
                return(<Card title={userLink.name}  style={{ width: 300 }}  extra={<div style={{color:"blue"}}
                onClick={
                    ()=>{
                        let args ={
                            "sessionId":props.sessionId,
                            "userLinkId":userLink.pid,
                            "userLinkS":props.userLinkS
                        }
                        actions.home.deleteUserLink(args);
                    }
                }>删除</div>}>
                <Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' }}>{userLink.name}</Avatar>
                <div>电话号码:{userLink.phone}</div>
                <div>电子邮件:{userLink.e_mail}</div>
                <div>职务:{userLink.job}</div>
                <div>地址:{userLink.address}</div>
                <div>描述:{userLink.description}</div>
        </Card>);SS
            })
        }
        <Card title="新建卡片"  style={{ width: 300 }}>
                <Input placeholder="姓名"
                value={props.new_name}
                onChange={
                    (e,value)=>{
                        let new_name = value;
                        let userLink_new = props.userLink_new;
                        userLink_new.name=value;
                        actions.home.save({userLink_new,new_name});
                    }
                }
                ></Input>
                <Input placeholder="电话号码"
                value={props.new_phone}
                onChange={
                    (e,value)=>{
                        let userLink_new = props.userLink_new;
                        let new_phone=value;
                        userLink_new.phone=value;
                        actions.home.save({userLink_new,new_phone});
                    }
                }></Input>
                <Input placeholder="电子邮件"
                value={props.new_e_mail}
                onChange={
                    (e,value)=>{
                        let new_e_mail = value;
                        let userLink_new = props.userLink_new;
                        userLink_new.e_mail=value;
                        actions.home.save({userLink_new,new_e_mail});
                    }
                }></Input>
                <Input placeholder="职务"
                value={props.new_job}
                onChange={
                    (e,value)=>{
                        let userLink_new = props.userLink_new;
                        let new_job = value;
                        userLink_new.job=value;
                        actions.home.save({userLink_new,new_job});
                    }
                }></Input>
                <Input placeholder="地址"
                value={props.new_address}
                onChange={
                    (e,value)=>{
                        let new_address = value;
                        let userLink_new = props.userLink_new;
                        userLink_new.address=value;
                        actions.home.save({userLink_new,new_address});
                    }
                }></Input>
                <Input type="textArea" placeholder="描述"
                value={props.new_description}
                onChange={
                    (e,value)=>{
                        let new_description = value;
                        let userLink_new = props.userLink_new;
                        userLink_new.description=value;
                        actions.home.save({userLink_new,new_description});
                    }
                }></Input>
                <Button
                onClick={
                    ()=>{
                        let args={
                            "sessionId":props.sessionId,
                            "userLink":props.userLink_new,
                            "userLinkS":props.userLinkS
                        }
                        actions.home.createUserLink(args);
                    }
                }>保存</Button>
        </Card>
  </div>
    );
}
export default UserLink;