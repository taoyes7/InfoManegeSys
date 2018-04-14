import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import { Upload, Button, Icon } from 'tinper-bee';
import {Avatar,Divider  } from 'uiw';
import "./index.less";
import Doc_menu from '../Doc_menu';
import Doc_dir from '../Doc_dir';
import Doc_docs from '../Doc_docs';

const Document= (props) =>{
  
  return (
      <div style={{maxHeight:700}}>
      {}
      <Doc_menu id="doc_menu" sessionId={props.sessionId}> </Doc_menu>
      <Divider id="doc_divider01"  type="vertical" />
      <Doc_dir id="doc_dir" sessionId={props.sessionId} currentDir={props._state.currentDir}> </Doc_dir>
      <Divider id="doc_divider02"  type="vertical" />
      <Doc_docs id="doc_docs"></Doc_docs>

          {/* <Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' }}>涛</Avatar> */}
      </div>
    
  );
}
   

export default Document;