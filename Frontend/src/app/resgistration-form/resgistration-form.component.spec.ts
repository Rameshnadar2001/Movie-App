import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResgistrationFormComponent } from './resgistration-form.component';

describe('ResgistrationFormComponent', () => {
  let component: ResgistrationFormComponent;
  let fixture: ComponentFixture<ResgistrationFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResgistrationFormComponent]
    });
    fixture = TestBed.createComponent(ResgistrationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
