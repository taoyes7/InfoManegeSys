import React from 'react';
import './index.less';
import {Menu,Divider} from 'uiw'
import { FormControl, Modal, Button,Upload,Icon,Radio,Tabs  } from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import Photo_AlbumModel from "../models/Photo_Album"
import Ablum from "../Ablum"
import Photo_Img from "../Photo_Img";

mirror.model(Photo_AlbumModel);
const Photo_Album = (props) => {//组件定义
    const Dragger = Upload.Dragger;
    const  RadioGroup = Radio.RadioGroup;
    const {TabPane} = Tabs;
    let key_ablum=0;
    let key_photo=0;
    const upLoadprops = {
        name: 'img',
        multiple: true,
        showUploadList: false,
        action: '/photo/upload/img/api',
        data:{
            "sessionId":props.sessionId,
            "apiTypeCode":props.state.api_typecode
        }, 
        onChange(info) {
          const status = info.file.status;
          if (status !== 'uploading') {
            console.log(info.file, info.fileList);
          }
          if (status === 'done') {
              let key=0;
            if(info.file.response.success){
                let data = info.file.response;
                let api_result = info.file.response;
                actions.photoAlbum.save({api_result});
                switch(data.typeCode){
                    case "0":
                     key=0;
                        let plantShow = (<div className="photo-layout">
                            <img src= {data.photo_src} alt="图片加载失败"></img>
                            <div>
                            {
                                data.plantS.map(function(plant){
                                    key++;
                                    return (
                                        <div key={key} className="photo-ablum-api-layout">
                                            <div>名称：{plant.name}</div>
                                            <div>&nbsp;&nbsp;相似度：{plant.score}</div>
                                        </div>
                                    )
                                })
                            }
                            </div>
                        </div>);
                        actions.photoAlbum.save({plantShow});
                    break;
                    case "1":
                     key=0;
                    let animalShow = (<div className="photo-layout">
                        <img src= {data.photo_src} alt="图片加载失败"></img>
                        <div>
                        {
                            data.animalS.map(function(animal){
                                key++;
                                return (
                                    <div key={key} className="photo-ablum-api-layout">
                                        <div>名称：{animal.name}</div>
                                        <div>&nbsp;&nbsp;相似度：：{animal.score}</div>
                                    </div>
                                )
                            })
                        }
                        </div>
                    </div>);
                    actions.photoAlbum.save({animalShow});
                    break;
                    case "2":
                     key=0
                    let foodShow = (<div className="photo-layout">
                        <img src= {data.photo_src} alt="图片加载失败"></img>
                        <div>
                        {
                            data.foodS.map(function(food){
                                key++;
                                return (
                                    <div key={key} className="photo-ablum-api-layout">
                                        <div>名称：{food.name}</div>
                                        <div>&nbsp;&nbsp;卡路里：{food.calorie}</div>
                                        <div>&nbsp;&nbsp;相似度：{food.probability}</div>
                                    </div>
                                )
                            })
                        }
                        </div>
                    </div>);
                    actions.photoAlbum.save({foodShow});
                    break;
                    case "3":
                     key=0;
                    let carShow = (<div className="photo-layout">
                        <img src= {data.photo_src} alt="图片加载失败"></img>
                        <div>
                        {
                            data.carS.map(function(car){
                                key++;
                                return (
                                    <div key={key} className="photo-ablum-api-layout">
                                        <div>名称：{car.name}</div>
                                        <div>&nbsp;&nbsp;相似度：{car.score}</div>
                                        <div>&nbsp;&nbsp;年份：{car.year}</div>
                                    </div>
                                )
                            })
                        }
                        </div>
                    </div>);
                    actions.photoAlbum.save({carShow});
                    break;
                    default:
                    break;
                }
              console.log(`${info.file.name} file uploaded successfully`);
            }else{
              console.log(`${info.file.name} file upload failed.`);
              alert(info.file.response.message);
            }
          } else if (status === 'error') {
            console.log(`${info.file.name} file upload failed.`);
          }
        },
      };
    return (
        <div className={props.className}>
        <div className="photo-album-layout">
        {
            props.ablumS.map(function(ablum){
                key_ablum++;
                // debugger;
                {console.log("ablum_photo")}
                return(
                    <Ablum ablum={ablum} key={key_ablum}></Ablum>
                )
            })
        }
        {(props.currentAblum.name=="root")&&(props.weiFenLei_ablum!=null)?<Ablum ablum={props.weiFenLei_ablum}></Ablum>:""}
        {
            props.photoS.map(function(photo){
                key_photo++;
                return(
                    <Photo_Img key={key_photo} photo={photo}></Photo_Img>
                )
            })
        }
        </div>
        <Divider className="photo-ablum-divider"   /> 
        <div style={{ marginTop: 16, height: 180 }}>
            <Dragger {...upLoadprops}>
            <p className="u-upload-drag-icon">
                <Icon type="inbox" className="uf-upload" />
            </p>
            <p className="u-upload-text">Click or drag file to this area to upload</p>
            <p className="u-upload-hint">Support for a single or bulk upload. Strictly prohibit from uploading company data or other band files</p>
        </Dragger>

        <Tabs
                    activeKey={props.state.api_typecode}
                    defaultActiveKey="0"
                    onChange={(activeKey)=>actions.photoAlbum.save({"api_typecode":activeKey})}
                    className="demo2-tabs"
                >
                    <TabPane tab="植物识别" key="0" >
                        {props.state.plantShow}
                    </TabPane>
                    <TabPane tab="动物识别" key="1">
                        {props.state.animalShow}
                    </TabPane>
                    <TabPane tab="菜品识别" key="2">
                        {props.state.foodShow}
                    </TabPane>
                    <TabPane tab="车型识别" key="3">
                        {props.state.carShow}
                    </TabPane>
                </Tabs>
        </div>
        </div>
    )
}
export default connect((state) => {//连接组件和状态管理
    return {
        state: state.photoAlbum,
        sessionId:state.login.sessionId,
        ablumS:state.photo.ablumS,
        weiFenLei_ablum:state.photo.weiFenLei_ablum,
        currentAblum:state.photo.currentAblum,
        photoS:state.photo.photoS
    }
})(Photo_Album)