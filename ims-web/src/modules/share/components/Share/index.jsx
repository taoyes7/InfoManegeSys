import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import { Upload, Button, Icon,Modal,FormControl } from 'tinper-bee';
import {Avatar,Divider ,Tag ,Notification} from 'uiw';
import "./index.less";
let check=true;
const Share= (props) =>{
  let key=0;
  
  if(check){
    actions.share.checkShare({"shareId":props.match.params.shareId});
    check=false;
  }
  return (
      <div id="share" >

      
      {
        props.state.show_file?
        <div>
              <div id="My_file_file" >
                <img id="file_img" alt="图片加载失败" id="file_img" src={props.state.file.iconPath}/>
                <p className="line-limit-length" id="file_name">{props.state.file.name}</p>
              </div>
                <div id="My_file_label" >
                {
                    props.state.file.labels.map(function(label){
                            key++;
                            return(
                            <Tag key={key}  color={label.color}>{label.content}</Tag>
                            )
                    })
                }
                </div>
                <a href={props.severPath+"/doc/share/download/file?fileId="+props.state.file.pid} download={props.state.file.name}>
                <Button shape="border"
                onClick={
                    ()=>{
                        Notification({
                            message: '成功',
                            description: props.state.file.name+'下载成功!',
                            type:"success",
                          });
                    }
                }>下载</Button>
                </a>
          </div>
        :""
      }

       <Modal
                    show={props.state.show_password}
                    onHide={()=>{
                      let show_password = false;
                      actions.share.save({show_password});
                    }}
                    style={{width: 200}}
                >
                 <Modal.Header className="text-center">
                        <Modal.Title>输入提取码：</Modal.Title>
                    </Modal.Header>
                    <Modal.Body >  
                        <FormControl className = "input-size"
                            value={props.state.password}
                            onChange={(e)=>{
                              let password =e;
                              actions.share.save({password});}}
                        />
                    </Modal.Body>
                        
                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                          let args={
                            "shareId":props.match.params.shareId,
                            "password":props.state.password
                          }
                          actions.share.getShareByPassword(args);
                    }}>
                            完成
                        </Button>
                    </Modal.Footer>
                </Modal>
        {console.log(props)}
      </div>
    
  );
}
   

export default Share;