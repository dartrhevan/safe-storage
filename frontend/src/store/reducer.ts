import { combineReducers } from 'redux';
import NoteReducer from "./Note/NoteReducer";
import AuthReducer from "./Auth/AuthReducer"

export default combineReducers({notes: NoteReducer, auth: AuthReducer});