import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OfficeDeleteComponent} from './office-delete.component';

describe('OfficeDeleteComponent', () => {
    let component: OfficeDeleteComponent;
    let fixture: ComponentFixture<OfficeDeleteComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [OfficeDeleteComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(OfficeDeleteComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
