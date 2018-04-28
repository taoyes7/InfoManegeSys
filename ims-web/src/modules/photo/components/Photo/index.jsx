import React from 'react';
import {Menu,Icon} from 'uiw'
import { FormControl, Modal, Button,Divider} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import Photo_menu from '../Photo_menu';
import Photo_Album from "../Photo_Album";
import './index.less';

const Photo = (props) => {//组件定义
    
    return (
        <div  style={{maxHeight:700}}>
        <Photo_menu className="photo-menu-size" args="nihao"></Photo_menu>
            {/* <Divider className="divider"  type="vertical" />  */}
            <Photo_Album  className="photo-album-size"></Photo_Album> 
        </div>
    )
}

export default Photo;