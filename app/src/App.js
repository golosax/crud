import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import ProductsList from './ProductsList';
import ProductEdit from './ProductEdit';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/products' exact={true} component={ProductsList}/>
                    <Route path='/product/:id' component={ProductEdit}/>
                </Switch>
            </Router>
        )
    }
}

export default App;