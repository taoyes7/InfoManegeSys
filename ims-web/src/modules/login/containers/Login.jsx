/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import Login from "../components/Login";
import LoginModel from "../models/Login";

//注入Model
mirror.model(LoginModel);

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/user') {
    // actions.user.load();
  }
});


export default connect((state) => state.login)(Login);