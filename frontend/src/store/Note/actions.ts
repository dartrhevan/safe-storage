import {ActionTypes} from '../actionTypes';
import {NoteAction} from "./NoteAction";
import Note from "../../model/Note";

export function SetCurrent(current : Note) : NoteAction {
    return new NoteAction(ActionTypes.SetCurrent, current);
}

export function UpdateList(list : Note[]) : NoteAction {
    return new NoteAction(ActionTypes.UpdateList, null, list);
}

export function RemoveNote(note : Note) : NoteAction {
    return new NoteAction(ActionTypes.RemoveNote, note);
}

export function EditNote(newNote : Note) : NoteAction {
    return new NoteAction(ActionTypes.EditNote, newNote);
}
