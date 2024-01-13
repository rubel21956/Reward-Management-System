import { TestBed } from '@angular/core/testing';

import { NbrApplicationService } from './nbr-application.service';

describe('NbrApplicationService', () => {
  let service: NbrApplicationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NbrApplicationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
