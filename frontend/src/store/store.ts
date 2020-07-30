import { createStore } from 'redux';
import reducer from "./reducer";

interface IAuth {
    username: string
}

interface INote {

}

interface IState {
    auth: IAuth
    note: INote
}

export default createStore(reducer, {auth:{username: ""}/*, note: {list: [], note: null}*/});