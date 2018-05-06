import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import {Input,Card,Avatar} from 'uiw';
import { Button,Modal } from 'tinper-bee';

import "./index.less";

const UserDiary = (props) =>{
    return (<div className="userDiary-size">
        &nbsp;&nbsp;<div>标题：</div>
        <Input className="userDiary-Input-size"
        value={props.userDiary_title}
        onChange={
            (e,value)=>{
                let userDiary_title= value;
                let userDiary_new = props.userDiary_new;
                userDiary_new.title=value;
                actions.home.save({userDiary_title,userDiary_new})
            }
        }
        ></Input>
        <div>正文：</div>
        <Input className="userDiary-Input-size"
        value={props.userDiary_content}
        onChange={
            (e,value)=>{
                let userDiary_content= value;
                let userDiary_new = props.userDiary_new;
                userDiary_new.content=value;
                actions.home.save({userDiary_content,userDiary_new})
            }
        }
        type="textarea" 
        rows={20} 
        ></Input>
        <div>创建日期：{props.userDiary_new.createDate_s}</div>
        <div>修改日期：{props.userDiary_new.updateDate_s}</div>
        <Button className="userDiary-Button-save"
        onClick={
            ()=>{
                let args={
                    "sessionId":props.sessionId,
                    "title":props.userDiary_new.title,
                    "content":props.userDiary_new.content,
                    "diaryId":props.userDiary_new.pid,
                    "userDiary_pages":props.userDiary_pages,
                    "userDiary_items":props.userDiary_items
                }
                actions.home.saveDiary(args);
            }
        }
        >保存</Button>
        </div>
    );
}
export default UserDiary;