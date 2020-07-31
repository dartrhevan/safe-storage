
import {ActionTypes} from "../actionTypes";

interface IAction {
    type: ActionTypes,
    username: string
}

interface IAuthState {
    username: string | null
}

export default function (state : IAuthState = {username: null}, action : IAction): IAuthState {
    switch (action.type) {
        case ActionTypes.SetUsername:
            return {...state, username: action.username}
        case ActionTypes.Logout:
            return {...state, username: ""}
        default: return state;
    }
}