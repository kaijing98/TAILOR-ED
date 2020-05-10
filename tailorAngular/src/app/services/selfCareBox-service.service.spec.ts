import { TestBed } from '@angular/core/testing';

import { SelfCareBoxServiceService } from './selfCareBox-service.service';

describe('SelfCareBoxServiceService', () => {
  let service: SelfCareBoxServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SelfCareBoxServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
