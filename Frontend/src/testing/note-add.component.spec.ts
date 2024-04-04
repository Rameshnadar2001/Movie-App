import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NoteAddComponent } from 'src/app/note-add/note-add.component';

import { FormsModule } from '@angular/forms';
import { Note } from 'src/app/models/note';
import { NoteService } from 'src/app/services/note.service';
import { NoteServiceStub } from './noteServiceStub';


describe('NoteAddComponent', () => {
  let component: NoteAddComponent;
  let fixture: ComponentFixture<NoteAddComponent>;
  let noteService: NoteService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({

      declarations: [ NoteAddComponent ],
      imports: [ FormsModule ],
      providers: [ { provide: NoteService, useClass: NoteServiceStub } ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoteAddComponent);
    noteService = fixture.debugElement.injector.get(NoteService);

    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call addNote() and raise alert when server successfully posts note', () => {
    const note: Note = {
      id: 301,
      title: "Dummy Note",
      content: "Dummy note contents for test code"
    }
    const spy = spyOn(noteService, "addNote").and.callThrough();
    spyOn(window, "alert"); 

    component.note = note;
    let button = fixture.debugElement.query(By.css('button'));

    button.triggerEventHandler("click", null);
    expect(window.alert).toHaveBeenCalledWith("Note Added");

    expect(spy).toHaveBeenCalledWith(note);
    
  });
});
