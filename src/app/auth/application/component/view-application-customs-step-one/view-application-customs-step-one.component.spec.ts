import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewApplicationCustomsStepOneComponent } from './view-application-customs-step-one.component';

describe('ViewApplicationCustomsStepOneComponent', () => {
  let component: ViewApplicationCustomsStepOneComponent;
  let fixture: ComponentFixture<ViewApplicationCustomsStepOneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewApplicationCustomsStepOneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewApplicationCustomsStepOneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
