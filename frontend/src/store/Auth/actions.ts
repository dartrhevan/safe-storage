import {ActionTypes} from "../actionTypes";
import AuthAction from "./AuthAction";

export function SetUsername(username : string) {
    return new AuthAction(ActionTypes.SetUsername, username);
}

export function Logout()  {
    return new AuthAction(ActionTypes.Logout)
}