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
    // actions.user.load();
  }
});

export default connect((state) => state.document)(Document);