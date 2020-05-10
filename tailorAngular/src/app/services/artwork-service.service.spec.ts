import { TestBed } from '@angular/core/testing';

import { ArtworkServiceService } from './artwork-service.service';

describe('ArtworkServiceService', () => {
  let service: ArtworkServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArtworkServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
