
import {authHeader} from "./auth-header";

export function getApiCommonHeaders() {
    return  { "Content-Type": "application/json", 
                "Authorization" : authHeader() };
}