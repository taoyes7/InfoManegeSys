/*
* home模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Login from './containers/Login'
import Register from './containers/Register'



const Routers = ({ match }) => (
	<div>
		<Route exact path={match.url} component={Login}/>
		<Route exact path={`${match.url}/register`} component={Register}/>
	</div>
);

export default Routers;