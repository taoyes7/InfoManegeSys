import React,{ Component } from 'react';
import './index.less';
import { Modal, Button,Collapse} from 'tinper-bee';

export default class Doc_docs extends Component{
    constructor(props) {
        super(props);
        this.state={
            Model_classifyRule:false,
            createClassfiyRule:false
        }
        this.classfiyRull_close_ok = this.classfiyRull_close_ok.bind(this);
        this.classfiyRull_open=this.classfiyRull_open.bind(this);
    }
    classfiyRull_close_ok(){
        this.setState({
            Model_classifyRule:false
        })
    }
    classfiyRull_open(){
        this.setState({
            Model_classifyRule:true
        })
    }
    render(){ 
        return(
            
            <div>
            <Button id="classfiyRule" className="buttom-min" colors="primary" onClick={this.classfiyRull_open}> 管理分类规则</Button>

            <Modal
            show={this.state.Model_classifyRule}
            onHide={this.classfiyRull_close_ok}
            style={{width: 450}}
        >
            <Modal.Header className="text-center">
                <Modal.Title>管理分类规则</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <div>查看规则</div>
                <div onClick={()=>this.setState({ createClassfiyRule: !this.createClassfiyRule })}>新建规则</div>
                <Collapse in={this.state.createClassfiyRule}>
                <div>
                    look;
                </div>
                </Collapse>
            </Modal.Body>

            <Modal.Footer className="text-center">
                <Button colors="primary" onClick={this.classfiyRull_close_ok}>
                    完成
                </Button>
            </Modal.Footer>
        </Modal>
        </div>
        )
    }
}