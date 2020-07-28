import {ActionTypes} from "../actionTypes";

export default class AuthAction {
    constructor(public type: ActionTypes, public username: string | null = null) {
    }
}
