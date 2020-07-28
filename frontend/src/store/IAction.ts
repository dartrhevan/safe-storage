import {ActionTypes} from "./actionTypes";
import Note from "../model/Note";

export interface IAction {
    type: ActionTypes,
    note: Note | null,
    list: Array<Note> | null
}