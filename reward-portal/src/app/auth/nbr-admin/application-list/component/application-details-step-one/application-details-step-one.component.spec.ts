import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationDetailsStepOneComponent } from './application-details-step-one.component';

describe('ApplicationDetailsStepOneComponent', () => {
  let component: ApplicationDetailsStepOneComponent;
  let fixture: ComponentFixture<ApplicationDetailsStepOneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationDetailsStepOneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationDetailsStepOneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
