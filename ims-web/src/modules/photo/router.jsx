/*
* photo模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Photo from './containers/Photo'



const Routers = ({ match }) => (
	<div>
		<Route exact path={match.url} component={Photo}/>
	</div>
);

export default Routers;