import React,{Component} from 'react';
import './index.css';

export default class Album extends Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            <div id="album">
            <img id="album_photo" src={this.props.src}/>
            <center><div id="title">默认相册</div></center>
            </div>
        )
    }

}