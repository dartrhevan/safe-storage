import {ActionTypes} from "./actionTypes";


interface IAction {
    type : ActionTypes
}

interface IState  {
    list: Array<Object>
    current: string | null
}

export default function (state: IState, action : IAction) {
    switch (action.type) {
        case ActionTypes.Init:
            break;
        case ActionTypes.SetCurrent:
            break;
        case ActionTypes.UpdateList:
            break;
        case ActionTypes.removeNote:
            break;
        case ActionTypes.editNote:
            break;
    }
}