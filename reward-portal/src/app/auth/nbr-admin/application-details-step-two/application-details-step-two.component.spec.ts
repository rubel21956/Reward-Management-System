import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationDetailsStepTwoComponent } from './application-details-step-two.component';

describe('ApplicationDetailsStepTwoComponent', () => {
  let component: ApplicationDetailsStepTwoComponent;
  let fixture: ComponentFixture<ApplicationDetailsStepTwoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationDetailsStepTwoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationDetailsStepTwoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
