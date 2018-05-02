import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider, Tag,Icon,Menu,Dropdown,Notification,Input } from 'uiw';
import {Tooltip, Modal, Label, FormGroup, Button} from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import { SSL_OP_CISCO_ANYCONNECT } from 'constants';

class Photo_Img extends Component{  
    constructor(props) {
        super(props);
        this.state={
        }
        
    }
    render(){  
        let shif=this;
        
            return(
                    <img src={shif.props.photo.src} className="photo-img-show-size" alt="图片加载失败" 
                    
                    ></img>
            )
        
    }
}

export default connect((state) => {//连接组件和状态管理
    return {
        sessionId:state.login.sessionId,
        severPath:state.login.severPath,
        labelGroups:state.docMenu.labelGroups,
        tags:state.addLabel.tags
    }
})(Photo_Img)