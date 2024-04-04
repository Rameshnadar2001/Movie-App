import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { NoteService } from 'src/app/services/note.service';

describe('NoteService', () => {
  let service: NoteService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(NoteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should make GET calls to server to fetch all notes', () => {
    const notes = [{
      "id": 200,
      "title": "Submit Practice Assignment",
      "content": "Complete developing solution for practice assignment and submit to hobbes for evaluation"
    }, {
      "id": 201,
      "title": "Close Issues on Challenge Assignment",
      "content": "Refactor solution code and close issues raised on the challenge assignment"
    }]

    service.getNotes().subscribe(data => {
      expect(data.length).toBe(2);
      expect(data).toEqual(notes);
      expect(data[0].id).toBe(200);
      expect(data[1].id).toBe(201);
    });

    const request = httpMock.expectOne("http://localhost:3000/notes");
    expect(request.request.method).toBe("GET");
    request.flush(notes);
  });

  it('should make POST calls to server to save note', () => {
    const blog =  {
      "id": 250,
      "title": "Refactor Challenge Solution",
      "content": "Refactor challenge solution code and get the code reviewed by mentor"
    };

    service.addNote(blog).subscribe(data => {
      expect(data).toEqual(blog);
      expect(data.title).toEqual("Refactor Challenge Solution");
    });

    const request = httpMock.expectOne("http://localhost:3000/notes");
    expect(request.request.method).toBe("POST");
    request.flush(blog);
  });

  afterEach(()=>{
    httpMock.verify();
  });
});
