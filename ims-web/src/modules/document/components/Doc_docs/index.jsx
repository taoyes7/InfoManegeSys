import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider  } from 'uiw';
import { Modal, FormControl, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import GroupFile from '../GroupFile';
import FreeScrollBar from 'react-free-scrollbar';

  const Doc_docs= (props)=>{ 

       let _key=0;
        return (<div id="doc_docs" > 
        <center><h1 >{props.currentDir.name}</h1></center>
        <FreeScrollBar>
        <Divider style={{margin:'0px'}}/> 
        { 

            props.rulesAndFiles.map(function(rulesAndFile){
                    _key=_key+1;
                    return(
                        <GroupFile key={"doc-"+_key} _key={"doc-"+_key} rulesAndFile={rulesAndFile}></GroupFile>
                        
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
        currentDir:state.document.currentDir
    }   
})(Doc_docs)