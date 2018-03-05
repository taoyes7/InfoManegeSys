import React,{ Component } from 'react';
import MyText from 'components/Utils/MyText'

export default class InfoTexta extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        let style={
            
        };
        return (
        <div style={style}>
        {
            this.props.datas.map(function(info_text){
                return (  <MyText name={info_text.name} value={info_text.value}></MyText>)
            })
        }
        </div>)
      }
}

