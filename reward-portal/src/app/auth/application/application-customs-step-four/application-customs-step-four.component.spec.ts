import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationCustomsStepFourComponent } from './application-customs-step-four.component';

describe('ApplicationCustomsStepFourComponent', () => {
  let component: ApplicationCustomsStepFourComponent;
  let fixture: ComponentFixture<ApplicationCustomsStepFourComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationCustomsStepFourComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationCustomsStepFourComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
