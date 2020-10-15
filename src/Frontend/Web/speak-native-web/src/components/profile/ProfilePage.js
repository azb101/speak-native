import React from "react";
import { authService }  from "../../services/auth.service";
import { PrivateRoute } from "../private-route/PrivateRoute";
import { Router, Route, Link } from 'react-router-dom';

export class ProfilePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: authService.currentAuthValue.user,
        }
    }

    render(){
        const { currentUser } = this.state;

        return (
            <div className="card" style={{width: 18 + 'rem'}}>
                <div className="card-body">
                    <h5 className="card-title">Hello from SpeakNative!</h5>
                    <h6 className="card-subtitle mb-2 text-muted">{currentUser.email}</h6>
                    <p className="card-text">
                        Here is some description about your profile
                    </p>
                    <Link to="/phraseAdmin" className="nav-item nav-link">Edit phrases</Link>
                </div>
            </div>
        );
    }
}