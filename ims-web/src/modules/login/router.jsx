/*
* home模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Login from './containers/Login'



const Routers = ({ match }) => (
	<div>
		<Route exact path={match.url} component={Login}/>
	</div>
);

export default Routers;