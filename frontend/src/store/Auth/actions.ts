import {ActionTypes} from "../actionTypes";


export function setUsername(username : string) {
    return {
        username,
        type: ActionTypes.SetUsername
    };/*new AuthAction(ActionTypes.SetUsername, username);*/
}

export function Logout()  {
    return { type: ActionTypes.Logout }//new AuthAction(ActionTypes.Logout)
}