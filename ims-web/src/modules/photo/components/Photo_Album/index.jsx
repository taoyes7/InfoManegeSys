import React from 'react';
import './index.less';
import {Menu,Icon} from 'uiw'
import { FormControl, Modal, Button} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import Photo_AlbumModel from "../models/Photo_Album"
import Ablum from "../Ablum"
import Photo_Img from "../Photo_Img";

mirror.model(Photo_AlbumModel);
const Photo_Album = (props) => {//组件定义
    let key_ablum=0;
    let key_photo=0;
    return (
        <div className={props.className}>
        <div className="photo-album-layout">
        {
            props.ablumS.map(function(ablum){
                key_ablum++;
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
        </div>
    )
}
export default connect((state) => {//连接组件和状态管理
    return {
        state: state.photoMenu,
        sessionId:state.login.sessionId,
        ablumS:state.photo.ablumS,
        weiFenLei_ablum:state.photo.weiFenLei_ablum,
        currentAblum:state.photo.currentAblum,
        photoS:state.photo.photoS
    }
})(Photo_Album)