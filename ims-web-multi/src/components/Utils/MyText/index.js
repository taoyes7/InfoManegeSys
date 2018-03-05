import React,{ Component } from 'react';

export default class MyText extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        return (
        <div >
            <p>{this.props.name} : {this.props.value}</p>
        </div>)
      }
}