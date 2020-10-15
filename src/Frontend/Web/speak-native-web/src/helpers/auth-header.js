
import  { authService } from "./../services/auth.service";

export function authHeader() {
    const currentUser = authService.currentAuthValue;
    
    if (currentUser && currentUser.token) {
        return `Bearer ${currentUser.token}`;
    }
    else {
        return '';
    }
}