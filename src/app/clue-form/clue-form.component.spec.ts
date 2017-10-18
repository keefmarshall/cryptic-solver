import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClueFormComponent } from './clue-form.component';

describe('ClueFormComponent', () => {
  let component: ClueFormComponent;
  let fixture: ComponentFixture<ClueFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClueFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
