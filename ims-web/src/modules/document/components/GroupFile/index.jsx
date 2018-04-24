import React, { Component }from 'react';
import './index.less';
import { FormControl, Modal, Button,Collapse, Icon} from 'tinper-bee';
import {Tag ,Menu , Dropdown,Card,Divider} from 'uiw';
import mirror, { actions, connect, render } from 'mirrorx';
import GroupFileModel from "../models/GroupFile";
import My_file from '../My_file';

//注入Model
mirror.model(GroupFileModel);

class GroupFile extends Component{
    constructor(props) {
        super(props);
        this.state={
            showRuleLabel:false,
            showFile:true
        }
    }
    
    //index
    render(){ 
        let shif =this;
        let key_ruleLable=0;
        let key_ruleFileType=0;
        let key_file=0;
        return (<div className="main-div"> 
                    <div onClick={()=> shif.setState({showFile:!shif.state.showFile})}>{shif.props.rulesAndFile.labelGroup.name}</div>
                   
                    <Collapse in={this.state.showRuleLabel}>
                    <div onClick={()=> shif.setState({showRuleLabel:!shif.state.showRuleLabel})}>
                    <div className="label-input-layout">
                        {shif.props.rulesAndFile.labelGroup.labels.map(function(label){
                            key_ruleLable++;
                            return(
                                
                                <Tag key={shif.props._key+"-"+key_ruleLable} color={label.color}  >{label.content}</Tag>
                            );
                            
                        })} 
                        </div>
                        <div className="label-input-layout">
                        {
                            shif.props.rulesAndFile.labelGroup.fileTypes.map(function(fileType){
                                key_ruleFileType++;
                                return(
                                    <Tag  key={shif.props._key+"-"+key_ruleFileType} color="#52575c"  >{fileType}</Tag>
                                )
                                
                            })
                            
                        }
                        </div>
                    </div>
                    </Collapse >
                    <Divider onClick={()=> shif.setState({showRuleLabel:!shif.state.showRuleLabel})} style={{margin:'0px'}}/>
                    {/* <Collapse in={this.state.showFile}> */}
                    <div className="layout-file">
                    {
                        shif.props.rulesAndFile.fileResponseDTOArrayList.map(function(file){
                            key_file++;
                            return(
                                <My_file key={key_file} file={file} sessionId={shif.props.sessionId}></My_file>
                            )
                            
                            
                        })
                    }
                    </div>
                    {/* </Collapse > */}
        </div>)
      } 
}


export default connect((state) => {//连接组件和状态管理
    return {
        state: state.groupFile,
        sessionId:state.login.sessionId
    }
})(GroupFile)