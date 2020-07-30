import {ActionTypes} from "../actionTypes";
import {NoteAction} from "./NoteAction";
import Note from "../../model/Note";


interface INoteState  {
    list: Array<Note>
    current: Note | null
}

export default function (state: INoteState = { list: [], current: null}, action : NoteAction) : INoteState {
    let noteIndex: number;
    switch (action.type) {
        case ActionTypes.SetCurrent:
            return {...state, current: action.note as Note}
        case ActionTypes.UpdateList:
            return {...state, list: action.list as Note[]};
        case ActionTypes.RemoveNote:
            noteIndex = state.list.indexOf(action.note as Note);
            return {...state, list: state.list.splice(noteIndex, 1)};
        case ActionTypes.EditNote:
            noteIndex = state.list.findIndex((note: Note) => note.id === (action.note as Note).id);
            state.list[noteIndex] = action.note as Note;
            return {...state};/*
        case ActionTypes.SetUsername:
            return {...state, current: action.note as Note}
        case ActionTypes.Logout:
            return {...state, current: action.note as Note}*/
        default: return state;
    }
}