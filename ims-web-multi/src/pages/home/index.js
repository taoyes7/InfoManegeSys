import React from 'react';
import ReactDOM from 'react-dom';
import Welcome from 'components/Welcome';
import Sliders from 'components/PageSlinders/Sliders';
import PerInfo from 'components/PerInfo'
import Document from 'components/Document'
import Router from 'react-router'
import './index.css';



class HomePage extends React.Component {
    constructor() {
      super();
      this.state = {
        Img : [
                require('E:/Download/photo01.jpg'),
                require('E:/Download/photo02.jpg'),
                require('E:/Download/photo03.jpg'),
            ],
        src: require('E:/Download/img_head.jpg'),
        album_src: require('E:/Download/img_head.jpg'),
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
      let doc_click = ()=>{
        Router.push({
          pathname: '/test.html',
          query: { modal: true },
          state: { fromDashboard: true }
      })
        
      };
      return (
        
          <div id="home">
            <div id="main"  >
                <Sliders
                    images={this.state.Img}
                    speed={1}
                    delay={2}
                    autoPlay={true}
                    autoParse={true}
                />
                <PerInfo src={this.state.src} info_text01={this.state.info_text01} motto={this.state.motto } 
            info_text02={this.state.info_text02} ></PerInfo> 
                <Document album_src={this.state.album_src} doc_click={doc_click}></Document>                        
            </div>
          </div>
        
      );
    }
  }
ReactDOM.render(<HomePage />, document.querySelector("#app") );