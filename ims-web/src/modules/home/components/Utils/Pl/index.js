import React,{ Component } from 'react';
export default class Pl extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        var style={
            
                height:this.props.height ,
        };
        return (
            <p style={style}></p>
        )
      }
}