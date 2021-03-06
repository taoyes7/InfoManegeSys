/*
* 路由表
* */
import React from "react";
import { Router, Route } from "mirrorx";
import Layout from "layout";
import BDM from "modules/bdm/router";
import Home from "modules/home/router"
import Document from "modules/document/router";
import Login from "modules/login/router";
import Share from "modules/share/router";
import Photo from "modules/photo/router";

const App = () => (
	<Router>
		<div><div>
			<Route path="/login" component={Login} />
		</div>
		<div>
			{/* <Route exact path="*" component={Layout} /> */}
			<Route path="/bdm" component={BDM} />
			<Route path="/home" component={Home} />
			<Route path="/document" component={Document} />
			<Route path="/share" component={Share} />
			<Route path="/photo" component={Photo} />
			<Route exact path="/" render={() => (
				<h3>请选择一个菜单</h3>
			)}/>
		</div>
		</div>
		
	</Router>
);

export default App;
