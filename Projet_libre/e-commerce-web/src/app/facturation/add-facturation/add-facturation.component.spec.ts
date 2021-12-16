import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFacturationComponent } from './add-facturation.component';

describe('AddFacturationComponent', () => {
  let component: AddFacturationComponent;
  let fixture: ComponentFixture<AddFacturationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddFacturationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFacturationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
