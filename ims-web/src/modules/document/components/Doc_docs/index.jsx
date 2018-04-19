import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider  } from 'uiw';
import { Modal, FormControl, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import My_file from '../My_file';

export default class Doc_docs extends Component{
    constructor(props) {
        super(props);
    }
    
    
    render(){ 
       let key=0;
       let shif = this;
        return (<div id="doc_docs" > 
        <center><h1>{this.props.currentDir.name}</h1></center>
        <Divider style={{margin:'0px'}}/>   
        <div id="doc_docs_files">
        { 
            
            this.props.fileList.map(function(file){
                key++;
                    return(
                        <My_file key={key} file={file} sessionId={shif.props.sessionId}></My_file>
                    ) 
            })
        }
        </div> 
        </div>)
      } 
}


// export default connect((state) => {//连接组件和状态管理
//     return {
//         fileList: state.document.fileList
//     }   
// })(Doc_docs)