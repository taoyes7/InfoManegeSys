import React,{ Component } from 'react';
import './index.less';

export default class Welcome extends Component{
  constructor(props) {
   super(props);
 }
  render(){
    return (<div>
      Hello {this.props.name}
    </div>)
  }
}
