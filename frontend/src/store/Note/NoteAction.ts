import {ActionTypes} from "../actionTypes";
import Note from "../../model/Note";

export class NoteAction {
    constructor(public type: ActionTypes, public note: Note | null = null, public list: Array<Note> | null = null) {
    }
}