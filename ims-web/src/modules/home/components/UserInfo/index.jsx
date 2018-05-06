import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import {Input} from 'uiw';
import { Button,Modal } from 'tinper-bee';

import "./index.less";

const UserInfo = (props) =>{
    let key_photo=0;
    let key_ablum=0;
    return (<div className="login-size">
    <div  className="login-layout">
    <h1>用户个人信息——查看/修改</h1>
    <br/>
    <br/>   
    <img src={props.src} alt="图片加载失败" className="home-userInfo-headImg" onClick={()=>{
        // let modal_userInfo_head_change = false;
        actions.home.save({modal_userInfo_head_change:true});
    }}></img>
    <div className="login-account"><div className="login-account-label">姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="必填"
          // value={props.userInfo.name}
          defaultValue={props.userInfo_change.name}
          onChange={(e,value)=>{
          let userInfo = props.userInfo_change;
          userInfo_change.name=value;
          actions.login.save({userInfo_change});    
          }}></Input></div>
    <div className="login-account"><div className="login-account-label">职业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
          // value={props.userInfo.job}
          defaultValue={props.userInfo_change.job}
          onChange={(e,value)=>{
          let userInfo_change = props.userInfo_change;
          userInfo_change.job=value;
          actions.login.save({userInfo_change});
          }}></Input></div>
    <div className="login-account"><div className="login-account-label">从业时间</div><Input className="login-account-input" placeholder="可选"
          // value={props.userInfo.job_date}
          defaultValue={props.userInfo_change.job_date}
          onChange={(e,value)=>{
          let userInfo_change = props.userInfo_change;
          userInfo_change.job_date=value;
          actions.login.save({userInfo_change});
          }}></Input></div>
    <div className="login-account"><div className="login-account-label">签名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
          // value={props.userInfo.motto}
          defaultValue={props.userInfo_change.motto}
          onChange={(e,value)=>{
          let userInfo_change = props.userInfo_change;
          userInfo_change.motto=value;
          actions.login.save({userInfo_change});
          }}></Input></div>
    <div className="login-account"><div 
    className="login-account-label">QQ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
          // value={props.userInfo.qq}
          defaultValue={props.userInfo_change.qq}
          onChange={(e,value)=>{
          let userInfo_change = props.userInfo_change;
          userInfo_change.qq=value;
          actions.login.save({userInfo_change});
          }}></Input></div>
    <div className="login-account"><div className="login-account-label">E-Mail&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
          // value={props.userInfo.e_mail}
          defaultValue={props.userInfo_change.e_mail}
          onChange={(e,value)=>{
          let userInfo_change = props.userInfo_change;
          userInfo_change.e_mail=value;
          actions.login.save({userInfo_change});
          }}></Input></div>
    <div className="login-account"><div className="login-account-label">地址&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><Input className="login-account-input" placeholder="可选"
          // value={props.userInfo.address}
          defaultValue={props.userInfo_change.address}
          onChange={(e,value)=>{
          let userInfo_change = props.userInfo_change;
          userInfo_change.address=value;
          actions.login.save({userInfo_change});
          }}></Input></div>

    <Button colors="primary" onClick={ 
      ()=>{ 
          let args ={
              "sessionId":props.sessionId,
              "userInfo":props.userInfo_change
          }
          actions.home.changeUserInfo(args).then((result)=>{
            if(result){
                alert("修改成功");
            }
          })
      }
      }>修改</Button>
    </div>

    <Modal
                    show={props.modal_userInfo_head_change}
                    onHide={()=>{
                        let modal_userInfo_head_change = false;
                        actions.home.save({modal_userInfo_head_change});
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>选择头像</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    <div className="photo-album-layout">
                        {
                            props.userInfo_ablumS.map(function(ablum){
                                key_ablum++;
                                return(
                                    <div key={key_ablum} onClick={
                                        ()=>{
                                            let args={
                                                "sessionId":props.sessionId,
                                                "ablumId":ablum.pid
                                            }
                                            actions.photo.justGetAblumData(args).then(
                                                (result)=>{
                                                    let {ablums:userInfo_ablumS,photos:userInfo_photoS} = result;
                                                    actions.home.save({userInfo_ablumS,userInfo_photoS});
                                                }
                                            );
                                        }
                                    }>
                                    <div className="photo-ablum-size-min">
                                    <img src={ablum.iconPath} className="photo-ablum-highlight-size-min" alt="图片加载失败" ></img>
                                        <div className="photo-ablum-title-min"><center><div>{ablum.name}</div></center></div>
                                    </div>
                                    </div>
                                )
                            })
                        }
                        {
                            props.userInfo_photoS.map(function(photo){
                                key_photo++;
                                return(
                                    <img key={key_photo} src={photo.src} className="photo-img-show-size-min" alt="图片加载失败"
                                    onClick={
                                        ()=>{
                                            let args={
                                                "sessionId":props.sessionId,
                                                "photoId":photo.pid
                                            }
                                            actions.home.changeUserInfo_Img(args).then(
                                                (result)=>{
                                                    if(result){
                                                        let modal_userInfo_head_change = false;
                                                        actions.home.save({modal_userInfo_head_change});
                                                    }
                                                }
                                            );
                                        }
                                    }></img>
                                )
                            })
                        }
                    </div>
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                        let modal_userInfo_head_change = false;
                        actions.home.save({modal_userInfo_head_change});
                    }}>
                            取消
                        </Button>
                    </Modal.Footer>
                </Modal>
  </div>
    );
}
export default UserInfo;