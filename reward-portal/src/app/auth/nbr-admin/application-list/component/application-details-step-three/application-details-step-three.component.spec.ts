import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationDetailsStepThreeComponent } from './application-details-step-three.component';

describe('ApplicationDetailsStepThreeComponent', () => {
  let component: ApplicationDetailsStepThreeComponent;
  let fixture: ComponentFixture<ApplicationDetailsStepThreeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationDetailsStepThreeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationDetailsStepThreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
