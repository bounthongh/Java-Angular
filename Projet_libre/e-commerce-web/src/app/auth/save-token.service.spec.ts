import { TestBed } from '@angular/core/testing';

import { SaveTokenService } from './save-token.service';

describe('SaveTokenService', () => {
  let service: SaveTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaveTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
