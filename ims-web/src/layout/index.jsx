import React, { Component } from 'react';
import { Link,actions } from "mirrorx";
import './index.less';
import {Menu,Icon} from 'uiw'

export default class App extends Component {
	render() {
		return (
			<div className="index">
				<Menu defaultActive="1" theme="dark" mode="horizontal"  className="index"  >
					<Menu.Item index="1" ><div onClick={()=>{
						actions.routing.push({
							pathname: `/home`
						});
					}}>首页</div></Menu.Item>
				</Menu>
			</div>
		);
	}
}
