/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import UserDiary from "../components/UserDiary";

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/home/userdiary') {
     
  }
});


export default connect((state) =>{
  return{
      sessionId:state.login.sessionId,
      userDiary_new:state.home.userDiary_new,
      userDiary_title:state.home.userDiary_title,
      userDiary_content:state.home.userDiary_content,
      userDiary_pages:state.home.userDiary_pages,
      userDiary_items:state.home.userDiary_items
  }
})(UserDiary);