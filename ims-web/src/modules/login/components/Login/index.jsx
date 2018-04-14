import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx"
import { Button } from 'tinper-bee';

const Login = () =>{
    
    return (
        <div id="login">
          <Button colors="primary" onClick={ actions.login.register}>注册按钮</Button>
          <Button colors="primary" onClick={ actions.login.login}>登录按钮</Button>
        </div>
      
    );
}
export default Login;