import React,{ Component } from 'react';
import './index.css';
import PhotoFrame from 'components/Utils/PhotoFrame'
import InfoTexts from 'components/Utils/InfoTexts'
export default class PerInfo extends Component{
    constructor(props) {
        super(props);
    }
    
    render(){
        return (<div id="PerInfo" >
        
            <table >
                <tr>
                    <td><PhotoFrame  id="img_head" color="#FFFFFF" src={this.props.src} ></PhotoFrame></td>
                    <td><InfoTexts id="info-text01" datas={this.props.info_text01} ></InfoTexts></td>
                </tr>
                <tr><td><InfoTexts id="motto" datas={this.props.motto} ></InfoTexts></td></tr>
                <tr><td><p>联系方式：</p></td></tr>
                <tr><td><InfoTexts id="info-text02" datas={this.props.info_text02} ></InfoTexts></td></tr>
            </table>
        
            
        </div>)
      } 
}