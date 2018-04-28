import React,{ Component } from 'react';
import './index.less';
import {Avatar,Divider, Tag,Icon,Menu,Dropdown,Notification,Input,CopyToClipboard } from 'uiw';
import {Tooltip,Switch, Modal, FormControl, Label, FormGroup, Button, Upload,Clipboard} from 'tinper-bee';
import mirror, { actions, connect } from "mirrorx";
import { SSL_OP_CISCO_ANYCONNECT } from 'constants';

class My_file extends Component{
    constructor(props) {
        super(props);
        this.state={
            model_label:false,
            model_file:false,
            isEdit:false,
            isShare:false,
            share_url:"",
            share_password:"",
            share_copyValue:"ss",
            show_shareUrl:false,
            share_isPrivate:false,
            edit_color:"black",
            refresh:false,
            downLoadData:null,
            shareType:{
                "description":"一天有效",
                "code":"0"
            },
            shareTypes:[
                {
                    "description":"一天有效",
                    "code":"0"
                },
                {
                    "description":"三天有效",
                    "code":"1"
                },
                {
                    "description":"一周有效",
                    "code":"2"
                },
                {
                    "description":"一月有效",
                    "code":"3"
                },
                {
                    "description":"永久有效",
                    "code":"4"
                }
            ]
        }
        
        this.item_click=this.item_click.bind(this);
    }
    
    item_click(){
        if(this.props.file.type=="dir"){
            let args={
                sessionId:this.props.sessionId,
                data:this.props.file
            }
            actions.document.handClickDir(args);
        }else{
            this.setState({
                model_file:true
            })
        }
    }
    render(){  
        let shif=this;
        let key =0;
        let tip_fileNmae=(
            <div>
                {this.props.file.name}
            </div>
        )
        let model_key=0;

        let key_share=0;
        const menu_shareType = (
            <Menu   
        style={{width:240}}
      >
            
        {
            shif.state.shareTypes.map(function(shareType){
                key_share++;
                return (
                    <Menu.Item index={""+key_share} key={key_share}>
                    <div
                    onClick={
                        ()=>{
                            shif.setState({
                                shareType:shareType
                            })
                        }
                    }
                    >{shareType.description}</div>
                    </Menu.Item>
                )
            })
        }

      </Menu>)

        let key_label_type=0;
        let key_label_label=0;
        let key_label_color=0;
        let key_label_detal=0;
        const menu_addLabel = (
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
                                            shif.props.file.labels.map(function(_label){
                                                    if(_label.content==label.content){
                                                        isHave=true;
                                                    }
                                            });
                                            if(!isHave){
                                            let _label={
                                                "content":label.content,
                                                "pid":label.pid,
                                                "color":tag.color,
                                                "score":tag.score,
                                                "level":tag.level        
                                            }
                                            let args={
                                                "sessionId": shif.props.sessionId,
                                                "file":shif.props.file,
                                                "label":_label
                                            }
                                            actions.document.addLabelToFile(args).then(
                                                (result)=>{
                                                    if(result){
                                                        shif.props.file.labels.push(_label);
                                                        shif.setState({
                                                            refresh:!shif.state.refresh
                                                        });
                                                    }
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

      </Menu>)
            return(
                <div id="My_file">
                <div id="My_file_file" onClick={this.item_click}>
                <img id="file_img" alt="图片加载失败" id="file_img" src={this.props.file.iconPath}/>
                <Tooltip defaultOverlayShown={false} inverse overlay={tip_fileNmae}>
                <p className="line-limit-length" id="file_name">{this.props.file.name}</p>
                </Tooltip>
                </div>
                <div id="My_file_label"  onClick={()=>{
                                 shif.setState({
                                    model_label:true
                                });}}>
                {
                    shif.props.file.labels.map(function(label){
                        if(key<3){
                            key++;
                            let tip_labelContent=(
                                <div>
                                    {label.content}
                                </div>
                            )   
                            return(
                            <Tooltip key={key} defaultOverlayShown={false} inverse overlay={tip_labelContent}>
                            <Tag className="label-limit-length" color={label.color}>{label.content}</Tag>
                            </Tooltip>
                            ) 
                        }
                       
                    })
                }
                
                </div>
                <Modal
                    show={shif.state.model_label}
                    onHide={()=>{
                        shif.setState({
                            model_label:false
                        })
                    }}
                    style={{width: 450}}
                >
                 <Modal.Header className="text-center">
                        <Modal.Title>标签管理</Modal.Title>
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
                        shif.props.file.labels.map(function(label){
                            model_key++;
                                return(
                                <Tag key={model_key}  color={label.color}
                                onClick={()=>{
                                    if(shif.state.isEdit){
                                        let args={
                                            "sessionId":shif.props.sessionId,
                                            "file":shif.props.file,
                                            "labelId":label.pid
                                        }
                                        actions.document.deleteLabelFromFile(args).then(
                                            (result)=>{
                                                if(result){
                                                    let count=0;
                                                    let _count=0;
                                                    shif.props.file.labels.map(function(_label){
                                                        if(_label.content==label.content){
                                                            _count=count;
                                                        }
                                                        count++;
                                                    });
                                                    shif.props.file.labels.splice(_count,1);
                                                    shif.setState({
                                                        refresh:!shif.state.refresh
                                                    });
                                                }
                                            }
                                        );
                                    }
                                }}
                                >{label.content}</Tag>
                                ) 
                        })
                    }
                    {shif.state.isEdit? <Dropdown menu={menu_addLabel}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button>添加标签</Button> <Icon type="arrow-down" />
                    </a>
                    </Dropdown>:""}
                    {shif.state.isEdit&&shif.props.file.type=="dir"? <Button
                    onClick={
                        ()=>{
                            let args={ 
                                "sessionId":shif.props.sessionId,
                                "fileId":shif.props.file.pid,
                                "dirId": shif.props.currentDir.pid
                                }
                            actions.document.deleteFile(args).then(
                                (result)=>{
                                    if(result){
                                        alert("删除成功");
                                        shif.setState({
                                            model_label:false
                                        })
                                    }
                                }
                            );
                        }
                    }
                    >删除</Button>:""}
                    </Modal.Body>
                        
                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                                shif.setState({
                                    model_label:false
                                })
                            
                            
                    }}>
                            完成
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Modal
                    show={shif.state.model_file}
                    onHide={()=>{
                        shif.setState({
                            model_file:false
                        })
                    }}
                    style={{width: 450}}
                >
                 <Modal.Header className="text-center">
                        <Modal.Title>文件管理</Modal.Title>
                    </Modal.Header>
                    <Modal.Body >  
                    <div className="layout-column">
                    <div id="My_file_file_detail" >
                    <img id="file_img" alt="图片加载失败" id="file_img" src={shif.props.file.iconPath}/>
                    <p className="line-limit-length" id="file_name">{shif.props.file.name}</p>
                    </div>
                    <div className="layout-row">
                    {
                    shif.props.file.labels.map(function(label){
                        
                            key_label_detal++;
                            let tip_labelContent=(
                                <div>
                                    {label.content}
                                </div>
                            )   
                            return(
                            <Tooltip key={key} defaultOverlayShown={false} inverse overlay={tip_labelContent}>
                            <Tag className="label-limit-length" color={label.color}>{label.content}</Tag>
                            </Tooltip>
                            ) 
                        }
                    )
                }
                
                </div>
                <div className="layout-row">
                <Button shape="border" onClick={()=>{shif.setState({isShare:true})}}>分享</Button>
                {shif.state.isShare?<div><Dropdown menu={menu_shareType}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <Button>{shif.state.shareType.description}</Button> <Icon type="arrow-down" />
                    </a>
                    </Dropdown>
                    <Switch 
                    checked={this.state.share_isPrivate}
                    onChange={(e)=>{
                        shif.setState({
                            share_isPrivate:!shif.state.share_isPrivate
                        })
                    }}
                    checkedChildren={"私"}
                    unCheckedChildren={"公"}
                    />
                    <Button
                    onClick={
                        ()=>{
                            let args={
                                "fileId":shif.props.file.pid,
                                "sessionId":shif.props.sessionId,
                                "isPrivate":shif.state.share_isPrivate?"yes":"no",
                                "shareTypeCode":shif.state.shareType.code
                            }
                            actions.share.getShareUrl(args).then((result)=>{
                                shif.setState({
                                    share_url:result.share_url,
                                    share_password:result.passworld,
                                    share_copyValue:shif.state.share_isPrivate?result.share_url+"——"+result.passworld:result.share_url,
                                    show_shareUrl:true
                                })
                            });
                        }
                    }
                    >生成连接</Button>
                    </div>
                    :""}
                    
                </div>
                {
                    shif.state.show_shareUrl?<div> 
                        <CopyToClipboard  text={shif.state.share_copyValue} tooltip="复制成功！">
                        <Clipboard ></Clipboard>
                        </CopyToClipboard>
                         
                        URL:<Input autoFocus  defaultValue={shif.state.share_url}></Input>
                        
                        {shif.state.share_isPrivate?
                        <div>提取码:<Input autoFocus  defaultValue={shif.state.share_password}></Input></div>
                        :""}
                    </div>:""}
                <div>
                <a href={shif.props.severPath+"/doc/download/file?fileId="+shif.props.file.pid+"&&sessionId="+shif.props.sessionId} download={shif.props.file.name}>
                <Button shape="border"
                onClick={
                    ()=>{
                        Notification({
                            message: '成功',
                            description: shif.props.file.name+'下载成功!',
                            type:"success",
                          });
                    }
                }>下载</Button>
                </a>
                <Button shape="border" colors="red"
                onClick={
                    ()=>{
                        let args={ 
                            "sessionId":shif.props.sessionId,
                            "fileId":shif.props.file.pid,
                            "dirId": shif.props.currentDir.pid
                            }
                        actions.document.deleteFile(args).then(
                            (result)=>{
                                if(result){
                                    alert("删除成功");
                                    shif.setState({
                                        model_file:false
                                    })
                                }
                            }
                        );
                    }
                }
                >删除</Button>
                </div>
                </div>
                    </Modal.Body>
                        
                    <Modal.Footer className="text-center">
                        <Button colors="primary" onClick={()=>{
                                shif.setState({
                                    model_file:false
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
        rulesAndFiles:state.document.rulesAndFiles,
        currentDir:state.document.currentDir,
        labelGroups:state.docMenu.labelGroups,
        tags:state.addLabel.tags
    }
})(My_file)