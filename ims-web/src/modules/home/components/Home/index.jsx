import React from 'react';
import ReactDOM from 'react-dom';
import { actions } from "mirrorx";
import Sliders from '../PageSlinders/Sliders';
import PerInfo from '../PerInfo'
import Document from '../Document'

import "./index.less";

const Home = ({Img,src,album_src,info_text01,motto,info_text02}) =>{
    return (
        <div id="home">
          <div id="main"  >
        <Sliders
                  images={Img}
                  speed={1}
                  delay={2}
                  autoPlay={true}
                  autoParse={true}
              />
              <PerInfo src={src} info_text01={info_text01} motto={motto } 
          info_text02={info_text02} ></PerInfo>  
              <Document album_src={album_src} doc_click={actions.home.doc_click}></Document>                      
          </div>
        </div>
      
    );
}
export default Home;