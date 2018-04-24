import React from 'react';
import './index.less';
import { FormControl, Modal, Button,Collapse, Icon} from 'tinper-bee';
import mirror, { actions, connect, render } from 'mirrorx';
import CreateDirModel from "../models/CreateDir";

//注入Model
mirror.model(CreateDirModel);

const CreateDir = (props) => {//组件定义
    const createDir = () => {
        console.log(props);
        let args={
            "newDir_name":props.state.newDir_name,
            "sessionId":props.sessionId,
            "rulesAndFiles":props.rulesAndFiles
        }
        actions.createdir.createDir(args);
    }
    return (
        <div>
            <Button className="newDir" shape="border" onClick={() => actions.createdir.newDir_open()}>   
            <Icon type="uf-add-c-o"></Icon>新建文件夹
            </Button>
            <Modal
                    show={props.state.showModal}
                    onHide={actions.createdir.newDir_close_ok}
                    style={{width: 450}}
                >
                    <Modal.Header className="text-center">
                        <Modal.Title>新建文件夹</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                            文件名：
                        <FormControl
                        value={props.state.newDir_name}
                        onChange={(e)=>actions.createdir.newDir_onChange(e)}
                        />
                    </Modal.Body>

                    <Modal.Footer className="text-center">
                        <Button bordered style={{ marginRight: 20 }} onClick={()=>actions.createdir.newDir_close_cancel()}>
                            取消
                        </Button>
                        <Button colors="primary" onClick={()=>createDir()}>
                            确认
                        </Button>
                    </Modal.Footer>
                </Modal>
        </div>
    )
}

export default connect((state) => {//连接组件和状态管理
    return {
        state: state.createdir,
        sessionId:state.login.sessionId,
        rulesAndFiles:state.document.rulesAndFiles
    }
})(CreateDir)