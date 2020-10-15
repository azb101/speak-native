import { handleResponse } from "../helpers/handle-response";
import { getApiCommonHeaders } from "../helpers/api-headers";
import config from "config";

export const userService = {
    getAll
};

// TODO refactor, should we even have this possibility???
function getAll() {
    const requestOptions = { method: "GET", headers: getApiCommonHeaders() };
    return fetch(`${config.apiUrl}/users`, requestOptions).then(handleResponse);
}