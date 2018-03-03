
import {Router, Route, hashHistory} from 'react-router';

import { App } from 'containers';
import { HomePage } from 'containers';


export default (
<Router history={hashHistory}>
    <Route path="/" component={App} />
    <Route path="/home" component={HomePage} />
</Router>
)
