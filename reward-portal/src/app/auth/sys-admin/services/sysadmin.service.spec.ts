import { TestBed } from '@angular/core/testing';

import { SysadminService } from './sysadmin.service';

describe('SysadminCustomsHouseService', () => {
  let service: SysadminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SysadminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
