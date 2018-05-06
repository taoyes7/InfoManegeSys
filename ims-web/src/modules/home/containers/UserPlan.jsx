/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import UserPlan from "../components/UserPlan";

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/home/userplan') {
     
  }
});


export default connect((state) =>{
  return{
      sessionId:state.login.sessionId,
      userPlan_title:state.home.userPlan_title,
      userPlan_content:state.home.userPlan_content,
      userPlan_level:state.home.userPlan_level,
      userPlan_levels:state.home.userPlan_levels,
      on_userPlanS:state.home.on_userPlanS,
      end_userPlanS:state.home.end_userPlanS,
}
})(UserPlan);