/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import Share from "../components/Share";
import ShareModel from "../models/Share";

//注入Model
mirror.model(ShareModel);

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/share') {
    // actions.user.load();
  }
});


export default connect((state) => {
    return {
        state:state.share,
        severPath:state.login.severPath,
        
    }
})(Share);