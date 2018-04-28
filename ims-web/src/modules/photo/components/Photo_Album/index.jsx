import React from 'react';
import './index.less';
import {Menu,Icon} from 'uiw'
import { FormControl, Modal, Button} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import Photo_AlbumModel from "../models/Photo_Album"

mirror.model(Photo_AlbumModel);
const Photo_Album = (props) => {//组件定义
    
    return (
        <div className={props.className}>
        </div>
    )
}
export default connect((state) => {//连接组件和状态管理
    return {
        state: state.photoMenu,
        sessionId:state.login.sessionId
    }
})(Photo_Album)