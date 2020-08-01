import { createStore } from 'redux';
import reducer from "./reducer";

const loginKey = 'login';

export default createStore(reducer, {auth:{username:  sessionStorage.getItem(loginKey)??""}/*, note: {list: [], note: null}*/});