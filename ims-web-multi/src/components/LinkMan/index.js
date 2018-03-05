import React,{ Component } from 'react';
import {Avatar,Divider  } from 'uiw';
import './index.css';
import Pl from 'components/Utils/Pl'
export default class LinkMain extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        return(
        <div id="LinkMan">
            <center><p id="title">联系人</p></center>
            <div>&nbsp;<Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' }}>涛</Avatar>
            &nbsp;&nbsp; 彭涛
            </div>
            <Pl height="5px"></Pl>
            <Divider style={{margin:'0px'}}/>
            <Pl height="5px"></Pl>
            <div>&nbsp;<Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' }}>张</Avatar>
            &nbsp;&nbsp; 张三
            </div>
            <Pl height="5px"></Pl>
            <Divider style={{margin:'0px'}}/>
            <Pl height="5px"></Pl>
            <div>&nbsp;<Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' }}>李</Avatar>
            &nbsp;&nbsp; 李四
            </div>
        </div>)
    }
}