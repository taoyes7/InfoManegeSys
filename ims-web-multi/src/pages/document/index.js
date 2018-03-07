import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';



class Document extends React.Component {
    constructor() {
      super();
    }
    render() {
      return (
        
          <div id="document">
            
          </div>
        
      );
    }
  }
ReactDOM.render(<Document />, document.querySelector("#app") );