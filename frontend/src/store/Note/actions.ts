import {ActionTypes} from '../actionTypes';
import Note from "../../model/Note";

export function SetCurrent(note : Note) {
    return {
        type: ActionTypes.SetCurrent,
        note
    }
}

export function UpdateList(list : Note[]) {
    return {
        type: ActionTypes.UpdateList,
        list
    }
}

export function RemoveNote(note : Note) {
    return {
        type: ActionTypes.RemoveNote,
        note
    }
}

export function EditNote(note : Note) {
    return {
        type: ActionTypes.EditNote,
        note
    }
}
