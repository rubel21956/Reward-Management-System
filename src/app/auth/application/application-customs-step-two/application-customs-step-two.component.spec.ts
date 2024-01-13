import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationCustomsStepTwoComponent } from './application-customs-step-two.component';

describe('ApplicationCustomsStepTwoComponent', () => {
  let component: ApplicationCustomsStepTwoComponent;
  let fixture: ComponentFixture<ApplicationCustomsStepTwoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationCustomsStepTwoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationCustomsStepTwoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
