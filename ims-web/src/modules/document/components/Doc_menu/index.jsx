import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider  } from 'uiw';
import { Modal, FormControl, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";


export default class Doc_menu extends Component{
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
        this.close_ok = this.close_ok.bind(this);
        this.close_cancel=this.close_cancel.bind(this);
        this.open = this.open.bind(this);
    }
    close_cancel() {
        console.log("cancel");
        this.setState({
            showModal: false
        });
    }
    close_ok(){
        console.log("ok");
        this.setState({
            showModal: false
        });
    }
    open() {
        this.setState({
            showModal: true
        });
    }
    handleUpLoadSingleFileChange(info) {  
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        
          if(info.file.response.success){
            console.log(`${info.file.name} file uploaded successfully`);
          }else{
            console.log(`${info.file.name} file upload failed.`);
            console.log(info.file.response.message);
            alert(info.file.response.message);
          }
        
      } else if (info.file.status === 'error') {
        console.log(`${info.file.name} file upload failed.`);
      }
    }
    
    render(){  
        const uploadDoc_props= {
            name: 'file1',
            action: '/doc/upLoadSingleFile',
            headers: {
              authorization: 'authorization-text'
            },
            data:{
                "sessionId":this.props.sessionId
            }, 
            multiple: true,
            showUploadList: true,
            onChange: this.handleUpLoadSingleFileChange.bind(this),
            accept:"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            beforeUpload(file){
                // let args={
                //     "sessionId":this.props.sessionId,
                //     "fileName":file.name
                // }
                // let data=actions.document.checkUpLoadFile(args);
                // let{success} = data;
                // if(success){
                //     return true;
                // }else{
                //     return false;
                // }
                return true;
            },
            
          };  
        return (<div id="doc_menu" > 
            <center><Avatar style={{ color: '#f56a00', backgroundColor: '#fde3cf' ,marginTop: '10%'}} size="large">涛</Avatar></center>
            <div id="doc_menu_upload" onClick={this.open}><center>上传新文件</center></div>
            <div id="doc_menu_myDoc"><center>我的文件夹</center></div>
            <Modal
                    show={this.state.showModal}
                    onHide={this.close}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>上传新文件</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                        <Upload {...uploadDoc_props}>
                            <Button shape="border">
                            <Icon type="uf-upload" /> Click to Upload
                            </Button>
                        </Upload>
                        
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={this.close_cancel} >
                            取消
                        </Button>
                        <Button colors="primary" onClick={this.close_ok}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>
        </div>)
      } 
}