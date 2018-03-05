import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import PerInfo from 'components/PerInfo'



class Test extends React.Component {
    constructor() {
      super();
      this.state = {
        src: require('E:/Download/img_head.jpg'),
        info_text01:[
          {"name":"姓名","value":"张雪"},
          {"name":"职业","value":"中学教师"},
          {"name":"从业时间","value":"两年"}
        ],
        motto:[{"name":"签名","value":"最好的生活, 最美的自己"}],
        info_text02:[
          {"name":"电话","value":"18010541632"},
          {"name":"QQ","value":"1982038972"},
          {"name":"E-Mail","value":"1982038972@qq.com"},
          {"name":"地址","value":"四川大学(江安校区)"}
        ]
    }
  }
    render() {
      return (
        <div> 
            <PerInfo src={this.state.src} info_text01={this.state.info_text01} motto={this.state.motto } 
            info_text02={this.state.info_text02}></PerInfo>   
        </div>
      );
    }
  }
ReactDOM.render(<Test />, document.querySelector("#app") );