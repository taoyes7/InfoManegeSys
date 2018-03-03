import React from 'react';
import ReactDOM from 'react-dom';
import Welcome from 'components/Welcome';
import Sliders from 'components/PageSlinders/Sliders';
import PerInfo from 'components/PerInfo'
import './index.css';


class HomePage extends React.Component {
    constructor() {
      super();
      this.state = {
        Img : [
                require('E:/Download/photo01.jpg'),
                require('E:/Download/photo02.jpg'),
                require('E:/Download/photo03.jpg'),
            ]
      }
    }
    render() {
      return (
        <body className='pt'>
            <div>
                <Sliders
                    images={this.state.Img}
                    speed={1}
                    delay={2}
                    autoPlay={true}
                    autoParse={true}
                />
                <PerInfo/>                                  
            </div>
        </body>
        
      );
    }
  }
ReactDOM.render(<HomePage />, document.querySelector("#app") );