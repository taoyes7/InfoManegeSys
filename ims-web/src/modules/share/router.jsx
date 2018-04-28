/*
* share模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Share from './containers/Share'



const Routers = ({ match }) => (
	<div>
		<Route exact path={`${match.url}/:shareId`} component={Share}/>
	</div>
);

export default Routers;