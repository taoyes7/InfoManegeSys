/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import Home from "../components/Home";
import HomeModel from "../models/Home";

//注入Model
mirror.model(HomeModel);

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/home') {
    // actions.user.load();
  }
});




export default connect((state) =>{
  return{
      _state:state.home,
      sessionId:state.login.sessionId
  }
})(Home);