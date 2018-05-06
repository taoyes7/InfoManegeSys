import React,{Component} from 'react';
import './index.css';
import {actions} from 'mirrorx';
import DiR from './DIR.png'

export default class DocumentManage extends Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            // <div id="documentManage" onClick={this.props.click}>
            // <center><div id="title">文件管理</div></center>
            // </div>
            <div id="album" onClick={()=>{
                actions.routing.push({
                    pathname: `/document`
                });
            }}>
            <img id="album_photo" src={DiR}/>
            <center><div id="title">文件管理</div></center>
            </div>
        )
    }

}