class AddList extends Component {
    constructor(props) {
    super(props);  
    changedPropertyNames: null;
    this.state=({
        showModal: false,
        refCbData: [],
        addOrChange: false,
        changeIndex: ""
    })
    }
    //在此处获取数据列表，组件渲染完成生命周期方法中；
    componentDidMount() {
      this.initData();
    }
    // 初始化数据
  initData = () => {
    axios.get(`${URL.host}${URL.project}currtype/list`).then(res => {
      if (res.data.success == "success") {
        let data = res.data.detailMsg.data.content.map((item, index) => {
          item.key = index;
          return item;
        });
        self.setState({
          dataSource: data
        });
      }
    });
  };

 //一些事件方法

 //渲染部分
 render(){
   return(
       <div>
           <Table
            bordered
            data={dataSource}
            columns={columns}
            getBodyWrapper={this.getBodyWrapper}
            onRowClick={this.rowClick}
          />
       </div>
   )
 }
}
//输出组件
export default AddList;