import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NoteAddComponent } from 'src/app/note-add/note-add.component';
import { NoteComponent } from 'src/app/note/note.component';
import { SearchComponent } from 'src/app/search/search.component';
import { NoteService } from 'src/app/services/note.service';
import { NoteServiceStub } from './noteServiceStub';

import { NoteViewComponent } from 'src/app/note-view/note-view.component';

describe('NoteViewComponent', () => {
  let component: NoteViewComponent;
  let fixture: ComponentFixture<NoteViewComponent>;
  let noteService: NoteService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoteViewComponent, SearchComponent, NoteAddComponent, NoteComponent ],
      imports: [ FormsModule ],
      providers: [{ provide: NoteService, useClass: NoteServiceStub }],
    })
      .compileComponents();

    fixture = TestBed.createComponent(NoteViewComponent);
    component = fixture.componentInstance;
    noteService = TestBed.inject(NoteService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getNotes when the component is created', () => {
    const spy = spyOn(noteService, "getNotes").and.callThrough();
    component.ngOnInit();
    expect(spy).toHaveBeenCalled();
    expect(component.notes).toEqual([{
      title: "Test Note",
      content: "Test note content for testing"
    }]);
  });
  
});
