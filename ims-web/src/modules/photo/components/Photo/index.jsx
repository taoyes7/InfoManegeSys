import React from 'react';
import {Menu,Icon,Divider} from 'uiw'
import { FormControl, Modal, Button} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import Photo_menu from '../Photo_menu';
import Photo_Album from "../Photo_Album";
import './index.less';

const Photo = (props) => {//组件定义
    
    return (
        <div className="photo-layout" style={{maxHeight:700}}>
        <Photo_menu className="photo-menu-size" args="nihao"></Photo_menu>
            <Divider className="photo-divider"  type="vertical" /> 
        <Photo_Album  className="photo-album-size"></Photo_Album> 
        </div>
    )
}

export default Photo;