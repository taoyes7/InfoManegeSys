import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider  } from 'uiw';
import { Modal, FormControl, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import GroupFile from '../GroupFile';
import FreeScrollBar from 'react-free-scrollbar';
import My_file from '../My_file';

  const Doc_docs= (props)=>{ 

       let _key=0;
       let key_file=0;
        return (<div id="doc_docs" > 
        <center><h1 >{props.currentDir.name}</h1></center>
        <FreeScrollBar>
        <Divider style={{margin:'0px'}}/> 
            <div onClick={()=>{actions.document.clearSelect()}}>{props.select_labelGroup.name}</div>
        <Divider style={{margin:'0px'}}/>
        
        <div className="layout-file">
            {
                props.select_fileList.map(function(file){
                    console.log(file);
                    key_file++;
                    return(
                        <My_file key={key_file} file={file} sessionId={props.sessionId}></My_file>
                    ) ;
                })
            }
        </div>
        { 
            props.rulesAndFiles.map(function(rulesAndFile){
                    _key=_key+1;
                    return(
                        <GroupFile className="rule-group" key={"doc-"+_key} _key={"doc-"+_key} rulesAndFile={rulesAndFile}></GroupFile>
                        
                    ); 
            })
        }
        </FreeScrollBar>
        </div>)
       
}

export default connect((state) => {//连接组件和状态管理
    return {
        rulesAndFiles:state.document.rulesAndFiles,
        sessionId:state.login.sessionId,
        currentDir:state.document.currentDir,
        select_labelGroup:state.document.select_labelGroup,
        select_fileList:state.document.select_fileList
    }   
})(Doc_docs)