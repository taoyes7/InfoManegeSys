import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import Sliders from '../PageSlinders/Sliders';
import PerInfo from '../PerInfo'
import Document from '../Document'
import {Menu,Icon,Card,Tag,Input} from 'uiw';
import {Pagination,Button} from 'tinper-bee';
import FreeScrollBar from 'react-free-scrollbar';
import "./index.less";
import UserPlan from '../../containers/UserPlan';

const Home = (props) =>{
    // {Img,src,album_src,info_text01,motto,info_text02}
    let key_diary=0;
    const clickHandler = () => {
        actions.home.doc_click();
    }
    return (
        <div id="home">
            <Menu defaultActive="1" theme="dark" mode="horizontal"  className="index"  >
                <Menu.Item index="1" ><div onClick={()=>{
                    actions.routing.push({
                        pathname: `/home/userlink`
                    });
                }}>联系人</div></Menu.Item>
                <Menu.Item index="1" ><div onClick={()=>{
                   let userDiary_new = {
                    pid:"",
                    title:"",
                    content:"",
                    createDate_s:"",
                    updateDate_s:""
                };
                   let userDiary_title="";
                   let userDiary_content="";
                   actions.home.save({userDiary_new,userDiary_title,userDiary_content});
                   actions.routing.push({
                       pathname: `/home/userdiary`
                   });
                }}>写日记 </div></Menu.Item>
                <Menu.Item index="1" ><div onClick={()=>{
                    actions.routing.push({
                        pathname: `/home/userplan`
                    });
                }}>计划安排</div></Menu.Item>
            </Menu>
          <div id="main"  >
        {/* <Sliders
                  images={props._state.Img}
                  speed={1}
                  delay={2}
                  autoPlay={true}
                  autoParse={true}
              /> */}
              <PerInfo src={props._state.src} info_text01={props._state.info_text01} motto={props._state.motto } 
          info_text02={props._state.info_text02} ></PerInfo>
          <div className="home-doc-diary-layout">
              <Document album_src={props._state.album_src} doc_click={ clickHandler}></Document> 
              <div className="home-diary-layout">
              <center><h3>个人日记</h3></center>
              {
                  props._state.userDiaryS_show.map(function(userDiary){
                    key_diary++;
                    return(
                            <Card key={key_diary} className="card-diary" onClick={
                                ()=>{
                                    let userDiary_new = userDiary;
                                    let userDiary_title=userDiary.title;
                                    let userDiary_content=userDiary.content;
                                    actions.home.save({userDiary_new,userDiary_title,userDiary_content});
                                    actions.routing.push({
                                        pathname: `/home/userdiary`
                                    });
                                }
                            }>
                            <div className="card-diary">
                                <div className="card-diary-title">{userDiary.title}</div>
                                <div className="card-diary-date">{userDiary.updateDate_s}</div>
                            </div>
                            </Card>
                    )
                  })
              }
              
              </div>  
              {
                  props._state.userDiary_pages>1?<div>
                <Pagination
                    first
                    last
                    prev
                    next
                    boundaryLinks
                    items={props._state.userDiary_pages}
                    maxButtons={5}
                    activePage={props._state.userDiary_curPage}
                    onSelect={(eventKey)=>{actions.home.changDiaryPage({
                        "eventKey":eventKey,
                        "userDiaryS":props._state.userDiaryS,
                        "userDiary_pages":props._state.userDiary_pages,
                        "userDiary_items":props._state.userDiary_items
                        
                    })}} />
                  </div>:""
              }
            </div>
            <div className="home-userPlan-layout"> 
            <center><h3>待办计划</h3></center>
            <FreeScrollBar>
            {
                props._state.on_userPlanS.map(function(userPlan){
                    let color="";
                    let tag="";
                    switch(userPlan.level){
                        case 0:
                        color="red";
                        tag="紧急事件"
                        break;
                        case 1:
                        color="orange";
                        tag="重要事件"
                        break;
                        case 2:
                        color="green";
                        tag="一般事件"
                        break;
                        default:
                        break;

                    }
                    return(
                    <Card title={userPlan.title}  style={{ width: 300 }}>
                    <Tag color={color}>{tag}</Tag>
                    <Card>
                    {userPlan.content}
                    </Card>
                    
                    <Button
                    onClick={
                        ()=>{
                            let args={
                                "sessionId":props.sessionId,
                                "userPlan":userPlan,
                                "on_userPlanS":props._state.on_userPlanS,
                                "end_userPlanS":props._state.end_userPlanS
                            }
                            actions.home.endPlan(args);
                        }
                    }
                    >结束计划</Button>

            </Card>)
                })
            }   
            </FreeScrollBar>
            </div>               
          </div>
        </div>
      
    );
}
export default Home;