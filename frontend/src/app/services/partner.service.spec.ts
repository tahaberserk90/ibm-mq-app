import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PartnerService } from './partner.service';
import { Partner } from '../models/partner.model';

describe('PartnerService', () => {
  let service: PartnerService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PartnerService]
    });
    service = TestBed.inject(PartnerService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch partners via GET', () => {
    const mockPartners: Partner[] = [
      { id: '1', alias: 'Partner 1', type: 'Type1', direction: 'INBOUND', application: 'App1', processedFlowType: 'MESSAGE', description: 'Description 1' },
      { id: '2', alias: 'Partner 2', type: 'Type2', direction: 'OUTBOUND', application: 'App2', processedFlowType: 'NOTIFICATION', description: 'Description 2' }
    ];

    service.getPartners().subscribe(partners => {
      expect(partners.length).toBe(2);
      expect(partners).toEqual(mockPartners);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/partners');
    expect(req.request.method).toBe('GET');
    req.flush(mockPartners);
  });

  it('should add a partner via POST', () => {
    const newPartner: Partner = { id: '3', alias: 'Partner 3', type: 'Type3', direction: 'INBOUND', application: 'App3', processedFlowType: 'MESSAGE', description: 'Description 3' };

    service.addPartner(newPartner).subscribe(partner => {
      expect(partner).toEqual(newPartner);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/partners');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newPartner);
    req.flush(newPartner);
  });

  it('should delete a partner via DELETE', () => {
    const partnerId = '1';

    service.deletePartner(partnerId).subscribe(response => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`http://localhost:8080/api/partners/${partnerId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should handle error when fetching partners', () => {
    const errorMessage = 'Failed to fetch partners';

    service.getPartners().subscribe(
      () => fail('Expected an error, not partners'),
      error => {
        expect(error.status).toBe(500);
        expect(error.statusText).toBe('Internal Server Error');
      }
    );

    const req = httpMock.expectOne('http://localhost:8080/api/partners');
    req.flush(errorMessage, { status: 500, statusText: 'Internal Server Error' });
  });

  it('should handle error when adding a partner', () => {
    const newPartner: Partner = { id: '3', alias: 'Partner 3', type: 'Type3', direction: 'INBOUND', application: 'App3', processedFlowType: 'MESSAGE', description: 'Description 3' };
    const errorMessage = 'Failed to add partner';

    service.addPartner(newPartner).subscribe(
      () => fail('Expected an error, not a partner'),
      error => {
        expect(error.status).toBe(400);
        expect(error.statusText).toBe('Bad Request');
      }
    );

    const req = httpMock.expectOne('http://localhost:8080/api/partners');
    req.flush(errorMessage, { status: 400, statusText: 'Bad Request' });
  });

  it('should handle error when deleting a partner', () => {
    const partnerId = '1';
    const errorMessage = 'Failed to delete partner';

    service.deletePartner(partnerId).subscribe(
      () => fail('Expected an error, not a success response'),
      error => {
        expect(error.status).toBe(404);
        expect(error.statusText).toBe('Not Found');
      }
    );

    const req = httpMock.expectOne(`http://localhost:8080/api/partners/${partnerId}`);
    req.flush(errorMessage, { status: 404, statusText: 'Not Found' });
  });
});
