import AuthAction from "./AuthAction";
import {ActionTypes} from "../actionTypes";


interface IAuthState {
    username: string | null
}

export default function (state : IAuthState = {username: null}, action: AuthAction): IAuthState {
    switch (action.type) {
        case ActionTypes.SetUsername:
            return {...state, username: action.username}
        case ActionTypes.Logout:
            return {...state, username: null}
        default: return state;
    }
}