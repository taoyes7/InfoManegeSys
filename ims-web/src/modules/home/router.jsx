/*
* home模块路由表
* */
import React from "react";
import { Route,Link } from "mirrorx";
import Home from './containers/Home';
import UserInfo from './containers/UserInfo'
import Layout from "layout";
import UserLink from './containers/UserLink'
import UserDiary from './containers/UserDiary'
import UserPlan from './containers/UserPlan'


const Routers = ({ match }) => (
	<div>
		<Route exact path={`${match.url}/*`} component={Layout} />
		<Route exact path={match.url} component={Home}/>
		<Route exact path={`${match.url}/userlink`} component={UserLink}/>
		<Route exact path={`${match.url}/userdiary`} component={UserDiary}/>
		<Route exact path={`${match.url}/userplan`} component={UserPlan}/>
	</div>
);

export default Routers;