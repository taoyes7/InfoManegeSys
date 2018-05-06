import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider, Tag,Icon,Menu,Dropdown,Notification,Input } from 'uiw';
import {Tooltip, Modal, Label, FormGroup, Button} from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import { SSL_OP_CISCO_ANYCONNECT } from 'constants';

class Photo_Img extends Component{  
    constructor(props) {
        super(props);
        this.state={
            isEdit:false,
            modal_changePhoto:false
        }
        
    }
    render(){  
        let shif=this;
        let key_photo_label=0;
        let key_label_type=0;
        let key_label_label=0;
        let key_label_color=0;
        const menu_add_Labels = (
            <Menu 
        defaultActive="1"
        defaultOpened={['5']}
        style={{width:240}}
      >
    
        {
            shif.props.labelGroups.map(function(labelGroup){
                key_label_type++;
                key_label_label=0;
                return (
                <Menu.SubMenu index={key_label_type} key={key_label_type} title={<span><Icon type="star-on" />{labelGroup.labelType.name}<span></span></span>}>
                {
                    labelGroup.labels.map(function(label){
                        key_label_label++;
                        key_label_color=0;
                    return(
                        <Menu.SubMenu  index={key_label_type+"-"+key_label_label} key={key_label_type+"-"+key_label_label} title={<span><Icon type="menu" /><span>
                        {label.content}
                        </span></span>}>
                        {
                        shif.props.tags.map(function(tag){
                            key_label_color++;
                            if(tag!=null){
                                return(
                                    <Menu.Item index={key_label_type+"-"+key_label_label+"-"+key_label_color} key={key_label_type+"-"+key_label_label+"-"+key_label_color}>
                                    <Tag className="tag"  color={tag.color}
                                    onClick={ ()=>
                                        {
                                            console.log(shif.props.rulesAndFiles);
                                            let isHave=false;
                                            shif.props.photo.labels.map(function(_label){
                                                    if(_label.content==label.content){
                                                        isHave=true;
                                                    }
                                            });
                                            if(!isHave){
                                            let _label={
                                                "score":tag.score,
                                                "description":label.description,
                                                "color":tag.color,
                                                "level":tag.level ,
                                                "pid":label.pid,
                                                "content":label.content
                                            }
                                            let args={
                                                "sessionId":shif.props.sessionId,
                                                "label":_label,
                                                "photoId":shif.props.photo.pid
                                            }
                                            actions.photo.addNewLabelToPhoto(args).then(
                                                (result)=>{
                                                    shif.props.photo.labels.push(_label);
                                                    shif.setState({
                                                        reflash:!shif.state.reflash
                                                    })
                                                }
                                            );
                                            }else{
                                                    alert("已拥有该标签");
                                            }
                                        } 
                                    }
                                    >{tag.color}</Tag>
                                    </Menu.Item>
                                )
                            }
                        })
                    }
                    </Menu.SubMenu> );
                    })
                }
                </Menu.SubMenu>
                )
            })
        }
    
      </Menu>
          );
        
            return(<div>
                    <img src={shif.props.photo.src} className="photo-img-show-size" alt="图片加载失败" 
                    onClick={
                        ()=>{
                            shif.setState({
                                modal_changePhoto:true
                            })
                        }
                    }
                    ></img>
                    <Modal
                    show={shif.state.modal_changePhoto}
                    onHide={()=>{
                        let modal_changePhoto = false;
                        shif.setState({
                            modal_changeAblum:modal_changePhoto
                        })
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>修改图片</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    <div className="layout-row-reverse"><div style={{color:shif.state.edit_color}}
                    onClick={()=>{
                        shif.setState({
                            edit_color:shif.state.isEdit?"black":"blue",
                            isEdit:!shif.state.isEdit
                        })
                    }}
                    >编辑</div><Icon type="edit"  style={{color:shif.state.edit_color}} /></div>
                    {
                        shif.props.photo.labels.map(function(label){
                            key_photo_label++;
                            return(
                                <Tag key={key_photo_label} color={label.color}
                                onClick={
                                    ()=>{
                                        if(shif.state.isEdit){
                                            let args={
                                                "sessionId":shif.props.sessionId,
                                                "labelId":label.pid,
                                                "photoId":shif.props.photo.pid
                                            }
                                            actions.photo.removeLabelFromPhoto(args).then(
                                                (result)=>{
                                                    if(result){
                                                        let count =0;
                                                        let _count =0;
                                                        shif.props.photo.labels.map(function(_label){
                                                            if(label.pid==_label.pid){
                                                                _count=count;
                                                            }
                                                            count++;
                                                        });
                                                        shif.props.photo.labels.splice(_count,1);
                                                        shif.setState({
                                                            reflash:!shif.state.reflash
                                                        })
                                                    }
                                                }
                                            );
                                        }
                                       
                                    }
                                }
                                >{label.content}</Tag>
                            )
                        })
                    }
                    {
                        shif.state.isEdit?
                        <div>
                            <Dropdown menu={menu_add_Labels}>
                            <a className="ant-dropdown-link" href="javascript:;">
                            <Button>选择标签</Button> <Icon type="arrow-down" />
                            </a>
                            </Dropdown>
                        </div>
                        :""
                    }
                    <Button
                        onClick={
                            ()=>{
                                let args = {
                                    "sessionId":shif.props.sessionId,
                                    "photoId":shif.props.photo.pid
                                }
                                actions.photo.deletePhoto(args).then(
                                    (result)=>{
                                        if(result){
                                            shif.setState({
                                                modal_changePhoto:false
                                            })
                                        }
                                    }
                                )
                            }
                        }
                    >删除图片</Button>
                    <div></div>
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                        let modal_changePhoto = false;
                        shif.setState({
                            modal_changePhoto:modal_changePhoto
                        })
                    }}>
                            完成
                        </Button>
                    </Modal.Footer>
                </Modal>
                    </div>
            )
        
    }
}

export default connect((state) => {//连接组件和状态管理
    return {
        sessionId:state.login.sessionId,
        severPath:state.login.severPath,
        labelGroups:state.docMenu.labelGroups,
        tags:state.addLabel.tags
    }
})(Photo_Img)