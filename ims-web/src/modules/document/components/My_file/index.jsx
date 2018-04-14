import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider, Tag } from 'uiw';
import { Modal, FormControl, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";

export default class My_file extends Component{
    constructor(props) {
        super(props);
    }
    
    
    render(){  
       
        return (<div id="My_file" >
        <div id="My_file_file">
            <img id="file_img" alt="图片加载失败" id="file_img" src={this.props.src}/>
            <p className="line-limit-length" id="file_name">{this.props.name}</p>
        </div> 
        
        <div id="My_file_label">
        {
            this.props.labels.map(function(label){
                return(
                    <Tag className="label-limit-length" color={label.color}>{label.name}</Tag>
                )
            })
        }
        </div>
        </div>)
       
      } 
}