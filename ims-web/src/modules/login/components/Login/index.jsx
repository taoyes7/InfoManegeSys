import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import {Input} from 'uiw';
import { Button } from 'tinper-bee';
import "./index.less"

const Login = (props) =>{
    
    return (
        <div className="login-size">
        <div  className="login-layout">
        <h1>用户文件信息管理系统</h1>
        <br/>
        <br/>
        <div className="login-account"><div className="login-account-label">用户名</div><Input className="login-account-input"
        onChange={(e,value)=>{
          let accountId = value;
          actions.login.save({accountId});
          }}
        ></Input></div>
        <div className="login-account"><div className="login-account-label">密码&nbsp;&nbsp;&nbsp;</div><Input type="password" className="login-account-input"
        onChange={(e,value)=>{
          let password = value;
          actions.login.save({password});
          }}
        ></Input></div>
        <div className="login-layout-row">
        &nbsp;&nbsp;
          <Button colors="primary" onClick={ ()=>{
            actions.routing.push({
              pathname: `/login/register`
          })
          }}>注册</Button>
          &nbsp;&nbsp;&nbsp;
          <Button colors="primary" onClick={ ()=>{
            if(props.state.accountId!=""&&props.state.password!=""){
              let args={
                "accountid":props.state.accountId,
                "passworld":props.state.password
              }
              console.log(args);
              actions.login.login(args);
            }else{
              alert("用户名密码均不能为空");
            }
            
          }}>登录</Button>
        </div >
          {/* <Button colors="primary" onClick={ actions.login.createRootDir}>创建根目录</Button> */}
        </div>
      </div>
    );
}
export default Login;