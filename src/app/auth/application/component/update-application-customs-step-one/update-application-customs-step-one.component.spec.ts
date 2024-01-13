import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateApplicationCustomsStepOneComponent } from './update-application-customs-step-one.component';

describe('UpdateApplicationCustomsStepOneComponent', () => {
  let component: UpdateApplicationCustomsStepOneComponent;
  let fixture: ComponentFixture<UpdateApplicationCustomsStepOneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateApplicationCustomsStepOneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateApplicationCustomsStepOneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
