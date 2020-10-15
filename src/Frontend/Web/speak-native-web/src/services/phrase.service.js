
import { handleResponse } from "../helpers/handle-response";
import { getApiCommonHeaders } from "../helpers/api-headers";
import config from "config";

const phraseAiUrl=`${config.apiUrl}/api/phrases`;

function addOne(phrase) {
    
    const requestOptions = { 
        method: "POST", 
        headers: getApiCommonHeaders(),
        body: JSON.stringify(phrase)
    };

    return fetch(phraseAiUrl, requestOptions).then(handleResponse);
}

function getAll(userId) {
    const url = `${phraseAiUrl}?userId=${userId}`;
    const requestOptions = { method: "GET", headers: getApiCommonHeaders() };

    return fetch(url, requestOptions).then(handleResponse);
}

function editOne(phrase) {    
    
}

function deleteAll(userId, phraseIds) {
    const requestOptions = { 
        method: "DELETE", 
        headers: getApiCommonHeaders(),
        body: JSON.stringify({
            userId,
            phraseIds
        })
    };

    return fetch(phraseAiUrl, requestOptions).then(handleResponse);
}


export const phraseService = {
    addOne,
    getAll,
    editOne,
    deleteAll
};
