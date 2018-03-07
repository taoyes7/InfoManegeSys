import React,{ Component } from 'react';
import './index.css';

export default class PhotoFrame extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        let style={
                height:'156px',
                width:'106px',
                background:this.props.color,
                padding:'7px' 
        };
        return (
        
        <div id="photoframe" style={style} >
            <center><img id="head_photo" src={this.props.src}/></center>
        </div>)
      }
}