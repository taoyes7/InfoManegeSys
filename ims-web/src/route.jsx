
import {Router, Route, hashHistory} from 'react-router';

import { App } from 'containers';
import { MyTest } from 'containers';

export default (
<Router history={hashHistory}>
    <Route path="/APP" component={App} />
    <Route path="/Test" component={MyTest}/>
</Router>
)
