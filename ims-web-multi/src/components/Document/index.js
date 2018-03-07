import React, {Component} from 'react';
import './index.css';
import Album from 'components/Document/Album'
import ActiveFile from 'components/Document/ActiveFile'
import DocumentManage from 'components/Document/DocumentManage'

export default class Document extends Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            
            <div id="Document">
                <Album src={this.props.album_src}></Album>
                <ActiveFile></ActiveFile>
                <DocumentManage></DocumentManage>
            </div>
        )
    }
}