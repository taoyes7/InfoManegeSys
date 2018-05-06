/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import UserInfo from "../components/UserInfo";

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/home/userinfo') {
      let userInfo_change = getState().home.userInfo;
      actions.home.save({userInfo_change});
      actions.photo.getRootAblum({"sessionId":getState().login.sessionId}).then(
        (result)=>{
          if(result){
            let args={
              "sessionId":getState().login.sessionId,
              "ablumId":getState().photo.rootAblum.pid
            }
            actions.photo.justGetAblumData(args).then(
                (result)=>{
                    let {ablums:userInfo_ablumS,photos:userInfo_photoS} = result;
                    actions.home.save({userInfo_ablumS,userInfo_photoS});
                }
            );
          }
        }
      );
    }
});


export default connect((state) =>{
  return{
      sessionId:state.login.sessionId,
      userInfo_change:state.home.userInfo_change,
      src:state.home.src,
      userInfo_ablumS:state.home.userInfo_ablumS,
      userInfo_photoS:state.home.userInfo_photoS,
      modal_userInfo_head_change:state.home.modal_userInfo_head_change
  }
})(UserInfo);