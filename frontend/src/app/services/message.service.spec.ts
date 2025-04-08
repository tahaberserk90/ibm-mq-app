import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MessageService } from './message.service';
import { Message } from '../models/message.model';

describe('MessageService', () => {
  let service: MessageService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MessageService]
    });
    service = TestBed.inject(MessageService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch messages via GET', () => {
    const mockMessages: Message[] = [
      { id: '1', timestamp: new Date(), status: 'Success', content: 'Message 1' },
      { id: '2', timestamp: new Date(), status: 'Failed', content: 'Message 2' }
    ];

    service.getMessages().subscribe(messages => {
      expect(messages.length).toBe(2);
      expect(messages).toEqual(mockMessages);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/messages');
    expect(req.request.method).toBe('GET');
    req.flush(mockMessages);
  });

  it('should fetch a message by ID via GET', () => {
    const mockMessage: Message = { id: '1', timestamp: new Date(), status: 'Success', content: 'Message 1' };

    service.getMessageById('1').subscribe(message => {
      expect(message).toEqual(mockMessage);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/messages/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockMessage);
  });

  it('should handle error when fetching messages', () => {
    const errorMessage = 'Failed to fetch messages';

    service.getMessages().subscribe(
      () => fail('Expected an error, not messages'),
      error => {
        expect(error.status).toBe(500);
        expect(error.statusText).toBe('Internal Server Error');
      }
    );

    const req = httpMock.expectOne('http://localhost:8080/api/messages');
    req.flush(errorMessage, { status: 500, statusText: 'Internal Server Error' });
  });

  it('should handle error when fetching a message by ID', () => {
    const errorMessage = 'Message not found';

    service.getMessageById('999').subscribe(
      () => fail('Expected an error, not a message'),
      error => {
        expect(error.status).toBe(404);
        expect(error.statusText).toBe('Not Found');
      }
    );

    const req = httpMock.expectOne('http://localhost:8080/api/messages/999');
    req.flush(errorMessage, { status: 404, statusText: 'Not Found' });
  });
});
