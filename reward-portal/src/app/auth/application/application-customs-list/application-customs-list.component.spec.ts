import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationCustomsListComponent } from './application-customs-list.component';

describe('ApplicationCustomsListComponent', () => {
  let component: ApplicationCustomsListComponent;
  let fixture: ComponentFixture<ApplicationCustomsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationCustomsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationCustomsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
