import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFacturationComponent } from './edit-facturation.component';

describe('EditFacturationComponent', () => {
  let component: EditFacturationComponent;
  let fixture: ComponentFixture<EditFacturationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditFacturationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFacturationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
