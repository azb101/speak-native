import { BehaviorSubject } from "rxjs";

import config from "config";
import { handleResponse } from './../helpers/handle-response.js';

const LOGIN_URL = `${config.apiUrl}/api/login`;
const USER_KEY = 'current-x-user';
const currentAuthSubject  = new BehaviorSubject(JSON.parse(localStorage.getItem(USER_KEY)));

export const authService = {
    login, 
    logout,
    currentAuthSubject: currentAuthSubject.asObservable(),
    get currentAuthValue() { return currentAuthSubject.value }
};

function login(email, password) {
    const requestOptions = {
        method : "POST",
        headers : { 'Content-Type' : 'application/json' },
        body : JSON.stringify({ email, password })
    }
    
    return fetch(LOGIN_URL, requestOptions)
        .then(handleResponse)
        .then( data => {
            localStorage.setItem(USER_KEY, JSON.stringify(data));
            currentAuthSubject.next(data);
            return data;
        });
}

function logout() {
    localStorage.removeItem(USER_KEY);
    currentAuthSubject.next(null);
}