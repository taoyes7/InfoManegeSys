import React,{ Component } from 'react';
import './index.less';
export default class PerInfo extends Component{
    constructor(props) {
        super(props);
    }
    render(){    
        return (<div id={this.props.id} > 
        <p onClick={this.props.click}>{this.props.text}</p>
        </div>)
      } 
}