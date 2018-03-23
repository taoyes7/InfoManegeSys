import React,{ Component } from 'react';
import './index.css';
import PhotoFrame from '../Utils/PhotoFrame'
import InfoTexts from '../Utils/InfoTexts'
import LinkMan from '../LinkMan'
import Pl from '../Utils/Pl'
export default class PerInfo extends Component{
    constructor(props) {
        super(props);
    }
    
    render(){
        
        return (<div id="PerInfo" >
        
            <table     border-spacing= "0px">
                <tr>
                    <td><PhotoFrame  id="img_head" color="#FFFFFF" src={this.props.src} ></PhotoFrame></td>
                    <td><InfoTexts id="info-text01" datas={this.props.info_text01}  width="116px" height_space="10px"></InfoTexts></td>
                </tr>
                <tr><td><InfoTexts id="motto" datas={this.props.motto} p_margin="8px"></InfoTexts></td></tr>
                <tr><td><p>联系方式：</p></td></tr>
                <tr><td><InfoTexts id="info-text02" datas={this.props.info_text02} ></InfoTexts></td></tr>
                <tr><td><Pl height="20px"></Pl><LinkMan></LinkMan></td></tr> 
            </table> 
        
            
        </div>)
      } 
}