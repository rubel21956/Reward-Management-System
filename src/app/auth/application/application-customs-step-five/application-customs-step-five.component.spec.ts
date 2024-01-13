import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationCustomsStepFiveComponent } from './application-customs-step-five.component';

describe('ApplicationCustomsStepFiveComponent', () => {
  let component: ApplicationCustomsStepFiveComponent;
  let fixture: ComponentFixture<ApplicationCustomsStepFiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationCustomsStepFiveComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationCustomsStepFiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
