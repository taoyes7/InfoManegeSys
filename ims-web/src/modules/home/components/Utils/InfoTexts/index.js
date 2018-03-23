import React,{ Component } from 'react';
import MyText from '../MyText'

export default class InfoTexta extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        {
            var self = this;
            var style={
                width:this.props.width
            };
        }
        
        return (
        <div style={style}>
        {
            this.props.datas.map(function(info_text){
                return (  <MyText name={info_text.name} value={info_text.value} p_margin={self.props.p_margin} height_space={self.props.height_space}></MyText>)
            })
        }
        </div>)
      }
}

