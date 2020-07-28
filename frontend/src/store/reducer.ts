import {ActionTypes} from "./actionTypes";
import {IAction} from "./IAction";
import Note from "../model/Note";


interface IState  {
    list: Array<Note>
    current: Note | null
    username: string | null
}

export default function (state: IState = { list: [], current: null, username: null}, action : IAction) : IState {
    let index: number;
    switch (action.type) {
        case ActionTypes.SetCurrent:
            return {...state, current: action.note as Note}
        case ActionTypes.UpdateList:
            return {...state, list: action.list as Note[]};
        case ActionTypes.RemoveNote:
            index = state.list.indexOf(action.note as Note);
            return {...state, list: state.list.splice(index, 1)};
        case ActionTypes.EditNote:
            index = state.list.indexOf(action.note as Note);
            state.list[index] = action.note as Note;
            return {...state};
        default: return state;
    }
}