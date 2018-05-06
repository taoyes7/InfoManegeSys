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
    let args={
      "sessionId":getState().login.sessionId
    }
    actions.home.loadUserInfo(args);
    actions.docMenu.getAllLabelType(args);
    actions.docMenu.getAllLabelsByGroup(args);
    actions.home.getAllUserLinkS(args);
    actions.home.getUserPlanS(args);
    actions.home.getUserDiaryS(args).then((result)=>{
      if(result){
        let _args={
          "eventKey":getState().home.userDiary_curPage,
          "userDiaryS":getState().home.userDiaryS,
          "userDiary_pages":getState().home.userDiary_pages,
          "userDiary_items":getState().home.userDiary_items
        }
        actions.home.changDiaryPage(_args);
      }
    });

  }
});




export default connect((state) =>{
  return{
      _state:state.home,
      sessionId:state.login.sessionId
  }
})(Home);