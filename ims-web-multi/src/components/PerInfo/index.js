import React,{ Component } from 'react';
import './index.css';

export default class PerInfo extends Component{
    constructor(props) {
        super(props);
    }
    componentDidMount(){
        var c=document.getElementById("myCanvas");
        var cxt=c.getContext("2d");
        cxt.fillStyle="#FFFFFF";
        cxt.fillRect(0,0,100,170);
    }
    render(){
        return (<div>
            <canvas id="myCanvas"></canvas>
        </div>)
      }
}