/*
* home模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Document from './containers/Document'
import Layout from "layout";


const Routers = ({ match }) => (
	<div>
		<Route exact path="*" component={Layout} />
		<Route exact path={match.url} component={Document}/>
	</div>
);

export default Routers;