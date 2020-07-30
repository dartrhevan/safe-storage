import {ActionTypes} from "../actionTypes";
import AuthAction from "./AuthAction";

export function setUsername(username : string) {
    return {
        username,
        type: ActionTypes.SetUsername
    };/*new AuthAction(ActionTypes.SetUsername, username);*/
}

export function Logout()  {
    return new AuthAction(ActionTypes.Logout)
}