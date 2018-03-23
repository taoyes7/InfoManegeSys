/*
* home模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Home from './containers/Home'



const Routers = ({ match }) => (
	<div>
		<Route exact path={match.url} component={Home}/>
	</div>
);

export default Routers;