import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationDetailsStepTwoAcceptedComponent } from './application-details-step-two-accepted.component';

describe('ApplicationDetailsStepTwoAcceptedComponent', () => {
  let component: ApplicationDetailsStepTwoAcceptedComponent;
  let fixture: ComponentFixture<ApplicationDetailsStepTwoAcceptedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationDetailsStepTwoAcceptedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationDetailsStepTwoAcceptedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
