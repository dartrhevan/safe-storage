export default class Note {
    constructor(public head: string, public text: string | null, public id: string, public date: Date | null) {
    }
}