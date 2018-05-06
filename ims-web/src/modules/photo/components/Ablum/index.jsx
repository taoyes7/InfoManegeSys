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
            reflash:false,
            new_img:"",
            modal_changeAblum:false,
            modal_ImgS:false,
            photoS:[],
            ablumS:[],
            isEdit:false,
            edit_color:"black",
            modal_ablum_delete:false
        }
        
    }

      
    
    
    render(){  

        let shif=this;

        let key_ablum_label=0;
        let key_label_type=0;
    let key_label_label=0;
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
                return(
                    <Menu.Item  index={key_label_type+"-"+key_label_label} key={key_label_type+"-"+key_label_label} title={<span><Icon type="menu" /><span>
                    {label.content}
                    </span></span>}>
                                <div 
                                onClick={ ()=>
                                    {
                                        let isHave=false;
                                        shif.props.ablum.labels.map(function(_label){
                                            if(label.content==_label.content){
                                                isHave=true;
                                            }
                                        });
                                        if(!(isHave)){
                                        let _label={
                                                    "pid":label.pid,
                                                    "content":label.content,
                                                    "description":label.description
                                                };
                                        let args={
                                            "sessionId":shif.props.sessionId,
                                            "label":_label,
                                            "ablumId":shif.props.ablum.pid
                                        }
                                        actions.photo.addNewLabelToAblum(args).then(
                                            (result)=>{
                                                shif.props.ablum.labels.push(_label);
                                                shif.setState({
                                                    reflash:!shif.state.reflash
                                                })
                                            }
                                        );
                                        }else{
                                            alert("该标签已添加");
                                        }
            
                                    } 
                                }
                                >{label.content}</div>
                </Menu.Item> );
                })
            }
            </Menu.SubMenu>
            )
        })
    }

  </Menu>
      );

        let key_ablum=0;
        let  key_photo=0;
        
            return(
                <div className="photo-ablum-size">
                {console.log("ablum")}
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
                    {
                        shif.props.ablum.labels.map(function(label){
                            key_ablum_label++;
                            return(
                                <Tag key={key_ablum_label} color="#52575c"
                                onClick={
                                    ()=>{
                                        if(shif.state.isEdit){
                                            let args={
                                                "sessionId":shif.props.sessionId,
                                                "labelId":label.pid,
                                                "ablumId":shif.props.ablum.pid
                                            }
                                            actions.photo.removeLabelFromAblum(args).then(
                                                (result)=>{
                                                    if(result){
                                                        let count =0;
                                                        let _count =0;
                                                        shif.props.ablum.labels.map(function(_label){
                                                            if(label.pid==_label.pid){
                                                                _count=count;
                                                            }
                                                            count++;
                                                        });
                                                        shif.props.ablum.labels.splice(_count,1);
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
                        <Button
                        onClick={()=>{
                            shif.setState({
                                modal_ablum_delete:true
                            })
                        }}
                        >删除相册</Button>
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
                <Modal
                    show={shif.state.modal_ablum_delete}
                    onHide={()=>{
                        let modal_ablum_delete = false;
                        shif.setState({
                            modal_ImgS:modal_ablum_delete
                        })
                    }}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>确认删除</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button  onClick={()=>{
                        let modal_ablum_delete = false;
                        shif.setState({
                            modal_ablum_delete:modal_ablum_delete
                        })
                    }}>
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>{
                        let modal_ablum_delete = false;
                        let args={
                            "sessionId":shif.props.sessionId,
                            "ablumId":shif.props.ablum.pid
                        }
                        actions.photo.deleteAblum(args).then(
                            (result)=>{
                                if(result){
                                    shif.setState({
                                        modal_ablum_delete:modal_ablum_delete,
                                        modal_changeAblum:false
                                    })
                                }
                            }
                        )
                        
                    }}>
                            删除
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