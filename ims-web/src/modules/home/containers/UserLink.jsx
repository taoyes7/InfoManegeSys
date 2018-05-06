/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import UserLink from "../components/UserLink";

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/home/userlink') {
     
  }
});


export default connect((state) =>{
  return{
      sessionId:state.login.sessionId,
      userLinkS:state.home.userLinkS,
      userLink_new:state.home.userLink_new,
      new_name:state.home.new_name,
      new_phone:state.home.new_phone,
      new_e_mail:state.home.new_e_mail,
      new_job:state.home.new_job,
      new_address:state.home.new_address,
      new_description:state.home.new_description,
  }
})(UserLink);