import React, {Component} from 'react';
import './index.css';
import Album from './Album'
import ActiveFile from './ActiveFile'
import DocumentManage from './DocumentManage'

export default class Document extends Component{
    constructor(props){
        super(props);
    }

    render(){
        return(
            
            <div id="Document">
                <Album src={this.props.album_src}></Album>
                <ActiveFile></ActiveFile>
                <DocumentManage click={this.props.doc_click}></DocumentManage>
            </div>
        )
    }
}