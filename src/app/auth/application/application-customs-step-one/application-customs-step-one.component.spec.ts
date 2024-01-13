import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationCustomsStepOneComponent } from './application-customs-step-one.component';

describe('ApplicationCustomsStepOneComponent', () => {
  let component: ApplicationCustomsStepOneComponent;
  let fixture: ComponentFixture<ApplicationCustomsStepOneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationCustomsStepOneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationCustomsStepOneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
