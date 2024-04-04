import { of } from "rxjs";
import { Note } from "src/app/models/note";

export class NoteServiceStub {
    getNotes() {
        return of([{
            title: "Test Note",
            content: "Test note content for testing"
        }] as Note[]);
    }

    addNote(note: Note) {
        return of(note);
    }
}