import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsHouseDetailsComponent } from './customs-house-details.component';

describe('CustomsHouseDetailsComponent', () => {
  let component: CustomsHouseDetailsComponent;
  let fixture: ComponentFixture<CustomsHouseDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomsHouseDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomsHouseDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
