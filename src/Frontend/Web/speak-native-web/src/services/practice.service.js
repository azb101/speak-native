
import { handleResponse } from "../helpers/handle-response";
import { getApiCommonHeaders } from "../helpers/api-headers";
import config from "config";

const GET_RANDOM_URL=`${config.apiUrl}/api/phrases`;
const INCREMENT_URL=`${config.apiUrl}/api/phrases/increment`;

function getRandomPhrases(userId) {

    const url = `${GET_RANDOM_URL}/${userId}`;
    const requestOptions = { method: "GET", headers: getApiCommonHeaders() };

    return fetch(url, requestOptions).then(handleResponse);
}

function incrementFailingRates(userId, phraseIds) {
    const requestOptions = { 
        method: "PUT", 
        headers: getApiCommonHeaders(), 
        body: JSON.stringify({
            userId,
            phraseIds
        })
    };
    
    return fetch(INCREMENT_URL, requestOptions).then(handleResponse);
}


export const practiceService = {
    getRandomPhrases,
    incrementFailingRates
};
