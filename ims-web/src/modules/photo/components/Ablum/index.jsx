import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider, Tag,Icon,Menu,Dropdown,Notification,Input } from 'uiw';
import {Tooltip, Modal, Label, FormGroup, Button} from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import { SSL_OP_CISCO_ANYCONNECT } from 'constants';
import DEFAULT_ABLUM from "./DEFAULT_ABLUM.jpg";

class Ablum extends Component{
    constructor(props) {
        super(props);
        this.state={
            new_img:"",
            modal_changeAblum:false,
            modal_ImgS:false,
            photoS:[],
            ablumS:[],
            isEdit:false,
            edit_color:"black"
        }
        
    }
    
    
    render(){  
        let shif=this;
        let key_ablum=0;
        let  key_photo=0;
        
            return(
                <div className="photo-ablum-size">
                    <img src={shif.props.ablum.iconPath} className="photo-ablum-highlight-size" alt="图片加载失败" 
                    onClick={
                        ()=>{
                           let args={
                            "sessionId":shif.props.sessionId,
                            "ablumId":shif.props.ablum.pid
                          }
                          actions.photo.openAblum(args).then((result)=>{
                            if(result){
                              actions.photo.getCurrentAblum(args);
                              actions.photo.getAblumData(args);
                            }
                          });
                        }
                    }
                    ></img>
                    <div className="photo-ablum-title"><center><div
                    onClick={()=>{
                        let modal_changeAblum = true;
                        shif.setState({
                            modal_changeAblum:modal_changeAblum
                        })
                    }}
                    >{shif.props.ablum.name}</div></center></div>
                    <Modal
                    show={shif.state.modal_changeAblum}
                    onHide={()=>{
                        let modal_changeAblum = false;
                        shif.setState({
                            modal_changeAblum:modal_changeAblum
                        })
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>修改相册</Modal.Title>
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
                        <Button
                        onClick={()=>{
                            let args={
                                "sessionId":shif.props.sessionId,
                                "ablumId":shif.props.ablum.pid
                            }
                            actions.photo.justGetAblumData(args).then(
                                (result)=>{
                                    let {ablums:ablumS,photos:photoS} = result;
                                    let modal_ImgS = true;
                                    shif.setState({
                                        modal_ImgS:modal_ImgS,
                                        "ablumS":ablumS,
                                        "photoS":photoS
                                    })
                                }
                            )
                        }}
                        >修改封面</Button>
                        <div></div>
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                        let modal_changeAblum = false;
                        shif.setState({
                            modal_changeAblum:modal_changeAblum
                        })
                    }}>
                            完成
                        </Button>
                    </Modal.Footer>
                </Modal>
                
                <Modal
                    show={shif.state.modal_ImgS}
                    onHide={()=>{
                        let modal_ImgS = false;
                        shif.setState({
                            modal_ImgS:modal_ImgS
                        })
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>选择封面</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    <div className="photo-album-layout">
                        {
                            shif.state.ablumS.map(function(ablum){
                                key_ablum++;
                                return(
                                    <div key={key_ablum} onClick={
                                        ()=>{
                                            let args={
                                                "sessionId":shif.props.sessionId,
                                                "ablumId":ablum.pid
                                            }
                                            actions.photo.justGetAblumData(args).then(
                                                (result)=>{
                                                    let {ablums:ablumS,photos:photoS} = result;
                                                    shif.setState({
                                                        "ablumS":ablumS,
                                                        "photoS":photoS
                                                    })
                                                }
                                            )
                                        }
                                    }>
                                    <div className="photo-ablum-size-min">
                                    <img src={ablum.iconPath} className="photo-ablum-highlight-size-min" alt="图片加载失败" ></img>
                                        <div className="photo-ablum-title-min"><center><div>{ablum.name}</div></center></div>
                                    </div>
                                    </div>
                                )
                            })
                        }
                        {
                            console.log(shif.props)
                        }
                        {
                            shif.state.photoS.map(function(photo){
                                key_photo++;
                                return(
                                    <img src={photo.src} className="photo-img-show-size-min" alt="图片加载失败"
                                    onClick={
                                        ()=>{
                                            let args={
                                                "sessionId":shif.props.sessionId,
                                                "photoId":photo.pid,
                                                "ablumId":shif.props.ablum.pid
                                            }
                                            actions.photo.changeAblumImg(args).then(
                                                (result)=>{
                                                    if(result){
                                                        let modal_ImgS = false;
                                                        shif.setState({
                                                            modal_ImgS:modal_ImgS
                                                        })
                                                    }
                                                }
                                            );
                                        }
                                    }></img>
                                )
                            })
                        }
                    </div>
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                        let modal_ImgS = false;
                        shif.setState({
                            modal_ImgS:modal_ImgS
                        })
                    }}>
                            取消
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
        tags:state.addLabel.tags,
        ablumS:state.photo.ablumS,
        weiFenLei_ablum:state.photo.weiFenLei_ablum,
        currentAblum:state.photo.currentAblum,
        photoS:state.photo.photoS
    }
})(Ablum)