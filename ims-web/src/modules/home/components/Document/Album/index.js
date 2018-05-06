import React,{Component} from 'react';
import {actions} from 'mirrorx';
import './index.css';
import ABLUM from './ABLUM.jpg';

export default class Album extends Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            <div id="album" onClick={()=>{
                actions.routing.push({
                    pathname: `/photo`
                });
            }}>
            <img id="album_photo" src={ABLUM}/>
            <center><div id="title">相册管理</div></center>
            </div>
        )
    }

}