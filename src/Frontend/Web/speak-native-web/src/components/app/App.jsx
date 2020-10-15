import React from 'react';
import { Router, Route, Link } from 'react-router-dom';

import { history } from '../../helpers/history';
import { authService } from '../../services/auth.service';
import { PrivateRoute } from './../private-route/PrivateRoute';
import { HomePage } from './../home/HomePage';
import { PracticePage } from './../practice/PracticePage';
import { LoginPage } from './../login/LoginPage';
import { ProfilePage } from "./../profile/ProfilePage";
import {PhraseAdminPage} from "./../profile/PhraseAdmin/PhraseAdminPage";

export class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: null
        };
    }

    componentDidMount() {
        authService.currentAuthSubject.subscribe(x => this.setState({ currentUser: x?.user }));
    }

    logout() {
        authService.logout();
        history.push('/');
    }

    render() {
        const { currentUser } = this.state;
        
        return (
            <Router history={history}>
                <div>
                    <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <div className="navbar-nav">
                            <Link to="/" className="nav-item nav-link">Home</Link>
                        </div>
                        <div className="navbar-nav">
                            <Link to="/practice" className="nav-item nav-link">Practice</Link>
                        </div>
                        <div className="navbar-nav">
                            {currentUser &&<Link to="/profile" className="nav-item nav-link">Profile</Link>}
                        </div>
                        <div className="navbar-nav ml-auto">
                            {!currentUser && <Link to="/login" className="nav-item nav-link">Login</Link>}
                            {currentUser && <div className="nav-item nav-link"> Hello, {currentUser.email}! <a onClick={this.logout} className="">Logout</a></div>}
                        </div>
                    </nav>
                    <div className="container p-5">
                        <div className="row">
                            <div className="col-md-12">
                                <Route exact path="/" component={HomePage} />
                                <PrivateRoute exact path="/practice" component={PracticePage} />
                                <PrivateRoute exact path="/profile" component={ProfilePage} />
                                <PrivateRoute exact path="/phraseAdmin" component={PhraseAdminPage} />
                                <Route exact path="/login" component={LoginPage} />
                            </div>
                        </div>
                    </div>
                </div>
            </Router>
        );
    }
}