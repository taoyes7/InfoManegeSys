import React,{ Component } from 'react';
import './index.less';
import { actions } from "mirrorx";
import ClassfiyRule from "../ClassfiyRule";
import {Menu, Dropdown, Avatar,Divider, Tag  } from 'uiw';
import {Select, Collapse,Breadcrumb, FormControl,Modal, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import CreateDir from "../CreateDir";
import AddLabel from "../AddLabel";
export default class Doc_dir extends Component{
    constructor(props) {
        super(props);
        this.back=this.back.bind(this);
    }
    back(){
        actions.document.backToParent(this.props.sessionId);
    }
    render(){ 
        let key_filePathArray = 0;
        return (<div id="doc_dir" >  
            <Breadcrumb>
            {
                this.props.filePathArray.map(function(pathFile){
                    key_filePathArray++;
                    return (
                        <Breadcrumb.Item key={key_filePathArray}>
                        {pathFile.name}
                      </Breadcrumb.Item>
                      
                    )
                })
            }
            </Breadcrumb>
            <div className="flex-row">
            <CreateDir className="newDir"></CreateDir>
            <AddLabel className="addLabel"></AddLabel>
            <Button className="back" shape="border" onClick={this.back}>   
                返回一上级
            </Button>
            </div>  
            <ClassfiyRule id="classfiyRule" dir={this.props.currentDir} sessionId ={this.props.sessionId}> 管理分类规则</ClassfiyRule>
        </div>)
      } 
}