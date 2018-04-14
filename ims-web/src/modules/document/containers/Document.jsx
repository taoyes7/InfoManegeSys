/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import Document from "../components/Document";
import DocumentModel from "../models/Document";

//注入Model
mirror.model(DocumentModel);
//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/document') {
    actions.document.openRootDir(getState().login.sessionId);
  }
});

export default connect((state) =>  {//连接组件和状态管理
  return {
      _state: state.document,
      sessionId:state.login.sessionId
  }
})(Document);
