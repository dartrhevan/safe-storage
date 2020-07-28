import {ActionTypes} from './actionTypes';
import {IAction} from "./IAction";
import Note from "../model/Note";

export function SetCurrent(current : Note) : IAction {
    return {
        type: ActionTypes.SetCurrent,
        note: current,
        list: null
    };
}