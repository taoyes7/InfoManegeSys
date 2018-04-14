import React,{ Component } from 'react';
import './index.less';
import { actions } from "mirrorx";
import {Avatar,Divider  } from 'uiw';
import { FormControl,Modal, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';

export default class Doc_dir extends Component{
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            newDir_name: ""
        };
        this.createDir = this.createDir.bind(this);
        this.newDir_close_ok = this.newDir_close_ok.bind(this);
        this.newDir_close_cancel=this.newDir_close_cancel.bind(this);
        this.newDir_open = this.newDir_open.bind(this);
    }
    newDir_close_cancel() {
        console.log("cancel");
        this.setState({
            showModal: false
        });
    }
    newDir_close_ok(){
        if(this.state.newDir_name!=""){
            this.createDir();
            this.setState({
                showModal: false
            });
        }else{
            alert("文件名不能为空");
        }
    }
    newDir_open() {
        this.setState({
            showModal: true
        });
    }
    createDir = () =>{
        let args={
            "sessionId":this.props.sessionId,
            "dirName":this.state.newDir_name
        }
        actions.document.createDir(args);
    }
    newDir_onChange = (e) => {
        this.setState({newDir_name: e});
    }
    render(){    
        return (<div id="doc_menu" > 
            <p>当前文件：{this.props.currentDir.name}</p>
            <p>当前路径：{this.props.currentDir.path}</p>
           <Button shape="border" onClick={this.newDir_open}>   
                <Icon type="uf-add-c-o" /> 新建文件夹
            </Button>

            <Modal
                    show={this.state.showModal}
                    onHide={this.newDir_close_ok}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>新建文件夹</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                            文件名：
                        <FormControl
                        value={this.state.newDir_name}
                        onChange={this.newDir_onChange}
                        />
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={this.newDir_close_cancel}>
                            取消
                        </Button>
                        <Button colors="primary" onClick={this.newDir_close_ok}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>
        </div>)
      } 
}