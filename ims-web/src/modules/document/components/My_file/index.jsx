import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider, Tag } from 'uiw';
import {Tooltip, Modal, FormControl, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";

export default class My_file extends Component{
    constructor(props) {
        super(props);
        this.item_click=this.item_click.bind(this);
    }
    
    item_click(){
        let args={
            sessionId:this.props.sessionId,
            data:this.props.file
        }
        actions.document.handClickFile(args);
    }
    render(){  
        let key =0;
        let tip_fileNmae=(
            <div>
                {this.props.file.name}
            </div>
        )
        
        if(this.props.file.labels!=null){   
            return(
                <div id="My_file">
                <div id="My_file_file" onClick={this.item_click}>
                <img id="file_img" alt="图片加载失败" id="file_img" src={this.props.file.iconPath}/>
                <Tooltip defaultOverlayShown={false} inverse overlay={tip_fileNmae}>
                <p className="line-limit-length" id="file_name">{this.props.file.name}</p>
                </Tooltip>
                </div>
                <div id="My_file_label">
                {
                    this.props.file.labels.map(function(label){
                        if(key<3){
                            key++;
                            let tip_labelContent=(
                                <div>
                                    {label.content}
                                </div>
                            )   
                            return(
                            <Tooltip key={key} defaultOverlayShown={false} inverse overlay={tip_labelContent}>
                            <Tag className="label-limit-length" color={label.color}>{label.content}</Tag>
                            </Tooltip>
                            ) 
                        }
                       
                    })
                }
                
                </div>
                </div>
            )
        }else{
            return(
                <div id="My_file">
                <div id="My_file_file" onClick={this.item_click}>
                <img id="file_img" alt="图片加载失败" id="file_img" src={this.props.file.iconPath}/>
                <Tooltip defaultOverlayShown={false} inverse overlay={tip_fileNmae}>
                <p className="line-limit-length" id="file_name">{this.props.file.name}</p>
                </Tooltip>
                </div>
                <div id="My_file_label">
                
                </div>
                </div>
            )
        }  
    }
}