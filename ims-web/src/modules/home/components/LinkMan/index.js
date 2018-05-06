import React,{ Component } from 'react';
import {Avatar,Divider  } from 'uiw';
import './index.css';
import  mirror, { actions, connect } from "mirrorx";
import Pl from '../Utils/Pl'
class LinkMain extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        let shif=this;
        let key = 0;
        return(
        <div id="LinkMan">
            <center><p id="title">联系人</p></center>
            {
                shif.props.userLinkS.map(function(userLink){
                    key++;
                    if(key<=5){
                        return(
                            <div>
                                <Divider style={{margin:'0px'}}/>
                                <Pl height="5px"></Pl>
                                <div>&nbsp;<Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' }}>{userLink.name}</Avatar>
                                &nbsp;&nbsp; {userLink.name}&nbsp;&nbsp;{userLink.phone}
                                </div>
                                <Pl height="5px"></Pl>
                            </div>
                        )
                    }
                })
            }
           
            
        </div>)
    }
}

export default connect((state) => {//连接组件和状态管理
    return {
        userLinkS:state.home.userLinkS
    }
})(LinkMain)