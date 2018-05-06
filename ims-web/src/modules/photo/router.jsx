/*
* photo模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Photo from './containers/Photo'
import Layout from "layout";


const Routers = ({ match }) => (
	<div>
		<Route exact path="*" component={Layout} />
		<Route exact path={match.url} component={Photo}/>
	</div>
);

export default Routers;