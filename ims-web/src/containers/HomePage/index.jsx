//文件引入
import React, { Component } from "react";
import { Link } from "mirrorx";
import axios from "axios";
import {Row,Col,Panel,Table,Select,Button, Modal,Form, FormControl,FormGroup,InputGroup,Icon,Input,Popconfirm, Animate, ControlLabel
} from "tinper-bee";
import "./index.css";
import { URL } from "components/util";

export default class HomePage extends Component {
  constructor() {
    super();
  }
  render() {
    return (  
      <div>
        <Navs />
        //渲染 Item 组件并且传值 url 和 type 和Animate 动画类型
        <Item url="/Item/Get" type="Index" Animate="zoomIn" />
      </div>
    );
  }
}