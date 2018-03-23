import React,{ Component } from 'react';
import Pl from '../Pl'
export default class MyText extends Component{
    constructor(props) {
        super(props);
    }
    render(){
    
            var style_p={
                margin:this.props.p_margin
            };
            var style_space={
                height:this.props.height_space
            }
        
        return (
        <div >
            <Pl height={this.props.height_space}></Pl>
            <p style={style_p}>{this.props.name} : {this.props.value}</p>
            <Pl height={this.props.height_space}></Pl>
        </div>)
      }
}