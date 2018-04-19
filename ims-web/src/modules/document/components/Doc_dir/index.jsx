import React,{ Component } from 'react';
import './index.less';
import { actions } from "mirrorx";
import {Menu, Dropdown, Avatar,Divider, Tag  } from 'uiw';
import {Select, Collapse,Breadcrumb, FormControl,Modal, Label, FormGroup, Button, Upload, Icon } from 'tinper-bee';

export default class Doc_dir extends Component{
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            showModelAddLabel: false,
            addLabel_create:true,
            addLabel_add:false,
            addLabel_create_color:false,
            newDir_name: "",
            newLabel_content:"",
            newLabel_describe:"",
            newLabel_color:"red",
            newLabel_level:1,
            newLabel_score:0.9,
            temp_labels:[],
            old_labels:[],
            tags:[
                {"color":"red" , "level":1, "score":0.9},
                {"color":"orange" , "level":2, "score":0.7},
                {"color":"yellow" , "level":3, "score":0.5},
                {"color":"green" , "level":4, "score":0}
            ]
        };
        this.createDir = this.createDir.bind(this);
        this.newDir_close_ok = this.newDir_close_ok.bind(this);
        this.newDir_close_cancel=this.newDir_close_cancel.bind(this);
        this.newDir_open = this.newDir_open.bind(this);
        this.back=this.back.bind(this);
        this.addLabel_open = this.addLabel_open.bind(this);
        this.addLabel_cancel = this.addLabel_cancel.bind(this);
        this.addLabel_ok = this.addLabel_ok.bind(this);
        this.newLabel_clear = this.newLabel_clear.bind(this);
        this.newLabelC_onChange=this.newLabelC_onChange.bind(this);
        this.newLabelD_onChange=this.newLabelD_onChange.bind(this);
        this.newLabel_add=this.newLabel_add.bind(this);
    }

    /**
     * 新建文件夹
     */
    
    newDir_close_cancel() {
        console.log("cancel");
        this.setState({
            showModal: false
        });
    }
    newDir_close_ok(){
        if(this.state.newDir_name!=""){
            this.createDir();
            this.setState({
                showModal: false
            });
        }else{
            alert("文件名不能为空");
        }
    }
    newDir_open() {
        this.setState({
            showModal: true
        });
    }
    createDir = () =>{
        let args={
            "sessionId":this.props.sessionId,
            "dirName":this.state.newDir_name
        }
        actions.document.createDir(args);
    }
    newDir_onChange = (e) => {
        this.setState({newDir_name: e});
    }
    /**
     * 返回上一级
     */

    back(){
        actions.document.backToParent(this.props.sessionId);
    }
    /**
     * 添加标签
     */
    addLabel_open(){
        this.setState({
            showModelAddLabel: true
        });
       
        let shif=this;
        let args={
            "sessionId":this.props.sessionId
        }
        actions.document.getAllLabels(args).then(function (result) 
                                            { 
                                                
                                                shif.setState({
                                                    old_labels:result
                                                })
                                            });
        
    }
    addLabel_ok(){
        let args={
            "sessionId":this.props.sessionId,
            "file":this.props.currentDir,
            "label":{
                "labels":this.state.temp_labels
            }
            
        }
        actions.document.addLabelForDir(args);
        this.setState({
            showModelAddLabel: false,
            temp_labels:[]
        });
    }
    addLabel_cancel(){
        this.setState({
            showModelAddLabel: false,
            temp_labels:[]
        });
    }
    /**
     * 创建新标签
     */
    newLabelC_onChange=(e)=>{
        this.setState({newLabel_content:e});
    }
    newLabelD_onChange=(e)=>{
        this.setState({newLabel_describe:e});
    }
    newLabel_clear(){
        
        this.setState({
            newLabel_describe:"",
            newLabel_content:""
        });
    }
    newLabel_add(){
        let shif=this;
        let isVaild=true;
        let isExist=true;
        this.state.temp_labels.map(function(label){
            if(label.content==shif.state.newLabel_content){
                isVaild=false;
            }
        })
        this.props.currentDir.labels.map(function(label){
            if(label!=null){
                if(label.content==shif.state.newLabel_content){
                    isExist=false;
                }
            }
            
        });
        if(isVaild&&isExist){
            let args={
                "sessionId":this.props.sessionId,
                "label":this.state.newLabel_content
            } 
            actions.document.checkLabel(args).then(function (result) 
            { 
                
                if(result){
                    let label={
                        "pid":"s",
                        "content":shif.state.newLabel_content,
                        "description":shif.state.newLabel_describe,
                        "color":shif.state.newLabel_color,
                        "level":shif.state.newLabel_level,
                        "score":shif.state.newLabel_score,
                        "isAdd":shif
                    }
                    let labels = shif.state.temp_labels;
                    labels.push(label);
                    shif.setState({
                        temp_labels:labels,
                        newLabel_content:"",
                        newLabel_describe:"",
                        newLabel_color:"red",
                        newLabel_level:1,
                        newLabel_score:0.9
                    })
                }else{
                    alert("该标签已存在请直接添加");
                }
            });
           
        }else{
            if(!isExist){
                alert("已拥有该标签");
            }else{
                alert("该标签已添加");
            }
        }
        
    }
   
    onSelect() {}
  onClose(index) {
    console.log("index::",index)
  }
    render(){  
        let key_tags=0;
        let key_templabel=0;
        let key_filePathArray = 0;
        let shif=this;
        let key_oldLabel=0;
        let key_label_list_type=0;
        let key_label_list_content=0;
        let key_label_list_color=0;
        const menu_addLabel = (
            <Menu 
        defaultActive="1"
        defaultOpened={['5']}
        style={{width:240}}
        onClose={this.onClose.bind(this)}
        onSelect={this.onSelect.bind(this)}
      >
        <Menu.Item index="1"><Icon type="date" />标签分类</Menu.Item>
        <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>统一标签类型</span></span>}>
          {
              this.state.old_labels.map(function(label){
                  console.log("2-"+key_label_list_content);
                key_label_list_content++;
                key_label_list_color=0;
                  return(
                    <Menu.SubMenu index={"2-"+key_label_list_content} key={key_oldLabel} title={<span><Icon type="menu" /><span>
                        <Tag className="tag"  color="#52575c">{label.content}</Tag>
                        </span></span>}>
                        {
                        shif.state.tags.map(function(tag){
                            key_label_list_color++;
                            if(tag!=null){
                                console.log("2-"+key_label_list_content+key_label_list_color);
                                return(
                                    <Menu.Item index={"2-"+key_label_list_content+key_label_list_color}>
                                    <Tag className="tag" key={key_tags} color={tag.color}
                                    onClick={ ()=>
                                        {
                                            let isVaild=true;
                                            let isExist=true;
                                            shif.state.temp_labels.map(function(_label){
                                                if(label.content==_label.content){
                                                    isVaild=false;
                                                }
                                            });
                                            shif.props.currentDir.labels.map(function(label){
                                                if(label!=null){
                                                    if(label.content==shif.state.newLabel_content){
                                                        isExist=false;
                                                    }
                                                }
                                                
                                            });
                                            if(isVaild&&isExist){
                
                                            let _label={
                                                        "pid":label.pid,
                                                        "content":label.content,
                                                        "description":label.description,
                                                        "color":tag.color,
                                                        "level":tag.level,
                                                        "score":tag.score,
                                                        "isAdd":true
                                                    }
                                                    let labels = shif.state.temp_labels;
                                                    labels.push(_label);
                                                    shif.setState({
                                                        temp_labels:labels
                                                    })
                                                }else{
                                                    if(!isExist){
                                                        alert("已拥有该标签");
                                                    }else{
                                                        alert("该标签已添加");
                                                    }
                                                }
                
                                        } 
                                    }
                                    >{tag.color}</Tag>
                                    </Menu.Item>
                                )
                                key_tags++;

                            }
                        })
                    }
                    </Menu.SubMenu>
                  );
                  key_oldLabel++;
              })
          }
        </Menu.SubMenu>
        <Menu.SubMenu index="2" title={<span><Icon type="menu" /><span>其他</span></span>}>
        </Menu.SubMenu>
      </Menu>
          );

        return (<div id="doc_dir" > 
            <Breadcrumb>
            {
                this.props.filePathArray.map(function(pathFile){
                    key_filePathArray++;
                    return (
                        <Breadcrumb.Item key={key_filePathArray}>
                        {pathFile.name}
                      </Breadcrumb.Item>
                      
                    )
                })
            }
            
            </Breadcrumb>

           <Button shape="border" onClick={this.newDir_open}>   
                <Icon type="uf-add-c-o" /> 新建文件夹
            </Button>
            <Button shape="border" onClick={this.addLabel_open}>   
                添加标签
            </Button>
            <Button shape="border" onClick={this.back}>   
                返回上一级
            </Button>
            
            <Modal
                    show={this.state.showModal}
                    onHide={this.newDir_close_ok}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>新建文件夹</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                            文件名：
                        <FormControl
                        value={this.state.newDir_name}
                        onChange={this.newDir_onChange}
                        />
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={this.newDir_close_cancel}>
                            取消
                        </Button>
                        <Button colors="primary" onClick={this.newDir_close_ok}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>


                <Modal
                    show={this.state.showModelAddLabel}
                    onHide={this.addLabel_ok}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>添加标签</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                    <div>
                        {
                            
                            this.state.temp_labels.map(function(label){
                                if(label!=null){
                                   return(<Tag key={key_templabel} className="tag" color={label.color} 
                                   onClick={()=>{
                                        let _labels=shif.state.temp_labels;
                                        let count=0;
                                        let _count=0;
                                        _labels.map(function(_label){
                                            if(_label==label){
                                                _count=count;
                                            }
                                            count++;
                                        });
                                        _labels.splice(_count,1);
                                        shif.setState({
                                            temp_labels:_labels
                                        }
                                    );
                                    console.log(shif.state.temp_labels);
                                   }} 
                                   >{label.content}</Tag>) 
                                   key_templabel++;
                                }
                            })
                        }
                    </div>
                    <div onClick={ ()=> this.setState({ addLabel_create: !this.state.addLabel_create })}>创建新标签</div>
                    <Collapse in={this.state.addLabel_create}>
                    <div >
                    <div key={0} className="label-input-layout">
                        <div className="label-input-tag">标签内容:</div>
                        <FormControl className = "input-size"
                            value={this.state.newLabel_content}
                            onChange={this.newLabelC_onChange}
                            />
                    </div>
                    <div key={1} className="label-input-layout">
                        <div className="label-input-tag">标签描述:</div>
                        <FormControl className = "input-size"
                        value={this.state.newLabel_describe}
                        onChange={this.newLabelD_onChange}
                        />
                    </div>

                    <div className="label-input-layout">
                    <div onClick={ ()=> this.setState({ addLabel_create_color: !this.state.addLabel_create_color })}>
                    <Tag className="tag" color={this.state.newLabel_color} >{this.state.newLabel_color}</Tag>
                    </div>
                    <Collapse  in={this.state.addLabel_create_color}>
                    <div >
                    {
                        this.state.tags.map(function(tag){
                            if(tag!=null){
                                return(
                                    <Tag className="tag" key={key_tags} color={tag.color}
                                    onClick={ ()=> shif.setState({ newLabel_color:tag.color,
                                                                   newLabel_level:tag.level,
                                                                   newLabel_score:tag.score,
                                                                   addLabel_create_color: !shif.state.addLabel_create_color
                                                                 })}
                                    >{tag.color}</Tag>
                                )
                                key_tags++;

                            }
                        })
                    }
                    </div>
                    </Collapse>
                    </div>
                    <div className="label-layout-button">
                    <Button className="buttom-min" colors="primary" onClick={this.newLabel_add} >
                            添加
                    </Button>
                    <Button className="buttom-min" colors="primary" onClick={this.newLabel_clear}>
                            清空
                    </Button>
                   
                    </div>
                    </div>
                    </Collapse>
                    
                    <Collapse in={this.state.addLabel_create}>
                    <Dropdown menu={menu_addLabel}>
                    <a className="ant-dropdown-link" href="javascript:;">
                    <div>添加标签</div> <Icon type="arrow-down" />
                    </a>
                    </Dropdown>
                    </Collapse>
                    </Modal.Body>
                    

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={this.addLabel_cancel}>
                            取消
                        </Button>
                        <Button colors="primary" onClick={this.addLabel_ok}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>
        </div>)
      } 
}