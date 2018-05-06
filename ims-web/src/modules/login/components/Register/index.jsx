import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import {Input} from 'uiw';
import { Button } from 'tinper-bee';
import "./index.less"

const Register = (props) =>{
//   userInfo:{
//     name:"",
//     job:"",
//     job_date:"",
//     motto:"",
//     qq:"",
//     e_mail:"",
//     address:"",
//     phone:"",
//     password:"",
//     ac_password:"" 
// }
    return (
        <div className="login-size">
        <div  className="login-layout">
        <h1>用户文件信息管理系统——注册</h1>
        <br/>
        <br/>
        <div className="login-account"><div className="login-account-label">姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="必填"
              // value={props.userInfo.name}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.name=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">职业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
              // value={props.userInfo.job}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.job=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">从业时间</div><Input className="login-account-input" placeholder="可选"
              // value={props.userInfo.job_date}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.job_date=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">签名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
              // value={props.userInfo.motto}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.motto=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">QQ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
              // value={props.userInfo.qq}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.qq=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">E-Mail&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
              // value={props.userInfo.e_mail}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.e_mail=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">地址&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
              // value={props.userInfo.address}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.address=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">手机号&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="必填"
              // value={props.userInfo.phone}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.phone=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input type="password" className="login-account-input" placeholder="必填"
              // value={props.userInfo.password}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.password=value;
              actions.login.save({userInfo});
              }}></Input></div>
        <div className="login-account"><div className="login-account-label">确认密码</div><Input type="password" className="login-account-input" placeholder="必填"
              // value={props.userInfo.ac_password}
              onChange={(e,value)=>{
              let userInfo = props.userInfo;
              userInfo.ac_password=value;
              actions.login.save({userInfo});
              }}></Input></div>

        <Button colors="primary" onClick={ 
          ()=>{
              if(props.userInfo.name!=""&&props.userInfo.phone!=""&&props.userInfo.password!=""&&props.userInfo.ac_password!=""){
                if(props.userInfo.password!=props.userInfo.ac_password){
                  alert("请确认密码一致")
                }else{
                  actions.login.register({"userInfo":props.userInfo});
                }
              }else{
                alert("必填项不能为空");
              }
            }
          }>注册</Button>
        </div>
      </div>
    );
}
export default Register;