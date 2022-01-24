import { TestBed } from '@angular/core/testing';

import { IbanValidationServiceService } from './iban-validation-service.service';

describe('IbanValidationServiceService', () => {
  let service: IbanValidationServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IbanValidationServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
