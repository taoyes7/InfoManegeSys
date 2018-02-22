//引入所需文件react核心代码
import React, { Component } from "react";
//引入自定义组件 Navs；
import { Navs } from "containers";
//引入要显示的图片路径并定义为img变量；
import img from "assets/images/tinpertest.png";
//引入样式修饰渲染元素;
import "./index.less";
//引入 tinper-bee 组件库中的开关组件；
import { Switch } from "tinper-bee";
//定义默认输出组件 是MyTest；
export default class MyTest extends Component {
  constructor() {
    super();
    //定义组件内部状态
    this.state = {
      isShow: false
    };
  }
  //定义事件
  onChange = () => {
  //事件执行修改内部state值
    this.setState({
      isShow: !this.state.isShow
    });
  };
  //渲染元素
  render() {
    let { isShow } = this.state;
    return (
      <div>
      //自定义组件使用
        <Navs />
        <div className="test_modul animated bounceInLeft">
          <div className="swith_modul">
          //tinper-bee组件引用并且传入 size 显示状态 事件等；
            <Switch
              size="lg"
              checked={isShow}
              onChange={this.onChange}
              checkedChildren={"显示"}
              unCheckedChildren={"隐藏"}
            />
          </div>
          //根据组件内部状态判断显示图片or 显示默认文字；
          {isShow ? (
            <img src={img} className="animated bounceIn" alt="" />
          ) : (
            <div className="img_default animated bounceIn">图片位置</div>
          )}
        </div>
      </div>
    );
  }
}