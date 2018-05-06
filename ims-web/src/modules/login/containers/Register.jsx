/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import Register from "../components/Register";

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/user') {
    // actions.user.load();
  }
});


export default connect((state) =>{
    return {
        userInfo:state.login.userInfo
    }
})(Register);