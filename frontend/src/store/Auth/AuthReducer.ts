
import {ActionTypes} from "../actionTypes";

interface IAction {
    type: ActionTypes,
    username: string
}

interface IAuthState {
    username: string
}

const loginKey = 'login';

export default function (state : IAuthState = {username: ""}, action : IAction): IAuthState {
    switch (action.type) {
        case ActionTypes.SetUsername:
            if(action.username !== "")
                sessionStorage.setItem(loginKey, action.username);
            return {...state, username: action.username}
        case ActionTypes.Logout:
            sessionStorage.removeItem(loginKey)
            return {...state, username: ""}
        default: return state;
    }
}