import React,{Component} from 'react';
import './index.css';

export default class DocumentManage extends Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            <div id="documentManage" onClick={this.props.click}>
            <center><div id="title">文件管理</div></center>
            </div>
        )
    }

}