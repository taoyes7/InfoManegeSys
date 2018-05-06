/**
 * 业务容器组件
 */

import React, { Component } from "react";
import mirror, { actions, connect } from "mirrorx";
import Photo from "../components/Photo";
import PhotoModel from "../models/Photo";

//注入Model
mirror.model(PhotoModel);

//全局HOOK函数

mirror.hook((action, getState) => {
  const { routing: { location } } = getState();
  if (action.type === "@@router/LOCATION_CHANGE" && location.pathname === '/photo') {
    // actions.user.load();
    actions.photo.getRootAblum({"sessionId":getState().login.sessionId}).then(
      (result)=>{
        if(result){
          let args={
            "sessionId":getState().login.sessionId,
            "ablumId":getState().photo.rootAblum.pid
          }
          actions.photo.openAblum(args).then((result)=>{
            if(result){
              actions.photo.getCurrentAblum(args);
              actions.photo.getUnClassfiyedAblum(args).then(()=>{
                actions.photo.getAblumData(args);
              });
            }
          });
        }
      }
    );
    
  }
});




export default connect((state) =>{
  return{
      state:state.photo,
      sessionId:state.login.sessionId
  }
})(Photo);