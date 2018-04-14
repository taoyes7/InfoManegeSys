import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import Sliders from '../PageSlinders/Sliders';
import PerInfo from '../PerInfo'
import Document from '../Document'

import "./index.less";

const Home = (props) =>{
    // {Img,src,album_src,info_text01,motto,info_text02}
    const clickHandler = () => {
        actions.home.doc_click();
    }
    return (
        <div id="home">
          <div id="main"  >
        <Sliders
                  images={props._state.Img}
                  speed={1}
                  delay={2}
                  autoPlay={true}
                  autoParse={true}
              />
              <PerInfo src={props._state.src} info_text01={props._state.info_text01} motto={props._state.motto } 
          info_text02={props._state.info_text02} ></PerInfo>  
              <Document album_src={props._state.album_src} doc_click={ clickHandler}></Document>                      
          </div>
        </div>
      
    );
}
export default Home;