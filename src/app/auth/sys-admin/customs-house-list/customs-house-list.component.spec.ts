import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsHouseListComponent } from './customs-house-list.component';

describe('CustomsHouseListComponent', () => {
  let component: CustomsHouseListComponent;
  let fixture: ComponentFixture<CustomsHouseListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomsHouseListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomsHouseListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
