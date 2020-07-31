import NoteDTO from "./model/Note";

export function login(username: string, password: string) {
    const body = new FormData();
    body.append("username", username);
    body.append("password", password);
    return fetch('/api/login',
        {
            method: "POST",
            body
        }).catch(console.log)
}

export function registration(username: string, password: string) {
    const body = new FormData();
    body.append("username", username);
    body.append("password", password);
    return fetch('/api/register',
        {
            method: "POST",
            body
        }).catch(console.log)
}

export function getUsername() {
    return fetch('/api/get-username').catch(console.log)
}

export function logout() {
    return fetch('/api/logout').catch(console.log)
}

export function addNote(note: NoteDTO) {
    const body = new FormData();
    body.append("head", note.head);
    body.append("text", note.text as string);
    body.append("date", note.date?.toISOString() as string);
    return fetch('/api/note',
        {
            method: "POST",
            body
        }).catch(console.log)
}


export function editNote(note: NoteDTO) {
    const body = new FormData();
    body.append("id", note.id as string);
    body.append("head", note.head);
    body.append("text", note.text as string);
    body.append("date", /*`${note?.date?.getFullYear()??2020}-${note?.date?.getMonth()??'08'}-${note?.date?.getDay()??'01'}`*/note?.date?.toISOString() as string);
    return fetch('/api/note',
        {
            method: "PUT",
            body
        }).catch(console.log)
}

export function listNotes() {
    return fetch('/api/note/list').catch(console.log)
}

export function getNoteDetails(id: string) {
    return fetch(`/api/note/${id}`).catch(console.log)
}

export function removeNote(id: string) {
    return fetch(`/api/note/${id}`, {method: "DELETE"}).catch(console.log)
}