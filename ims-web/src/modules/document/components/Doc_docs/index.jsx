import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider  } from 'uiw';
import { Modal, FormControl, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import My_file from '../My_file';

export default class Doc_docs extends Component{
    constructor(props) {
        super(props);
        this.state={
            labels:[
                {"color":"red", "name":"计算机"},
                {"color":"green", "name":"服务业"},
                {"color":"green", "name":"餐饮"}
            ]
        }
    }
    
    
    render(){  
    
        return (<div id="doc_docs" > 
        <My_file src={require('E:/Myfile/DOC.png')} name={"myfileasdaasdff"} labels={this.state.labels}></My_file>
        </div>)
      } 
}