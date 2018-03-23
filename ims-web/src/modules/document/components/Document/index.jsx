import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import Btn_upload from "./Btn_upload";
import { Upload, Button, Icon } from 'tinper-bee';
import "./index.less";
const props = {
    name: 'file',
    action: 'http://localhost:8085/test/upLoadSingleFile',
    headers: {
      authorization: 'authorization-text',
    },
    onChange(info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        console.log(`${info.file.name} file uploaded successfully`);
      } else if (info.file.status === 'error') {
        console.log(`${info.file.name} file upload failed.`);
      }
    },
  };

const Document= () =>{
  return (
      <div>
      {/* <Btn_upload id="btn_upload_img" text="图片上传" click={actions.document.img_upload}></Btn_upload> */}
      <form action="http://localhost:8085/test/upLoadSingleFile" method="POST" enctype="multipart/form-data" onsubmit="return false;">
          <p>单文件上传：</p><br/>
          <input type="file" name="file1"/>
          <input type="submit" value = "上传"/>
          {/* <Button shape="border">
          <Icon type="uf-upload" /> Click to Upload
          </Button> */}
      </form>
          
      </div>
    
  );
}
   

export default Document;