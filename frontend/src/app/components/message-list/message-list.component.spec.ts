import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { of, throwError } from 'rxjs';

import { MessageListComponent } from './message-list.component';
import { MessageService } from '../../services/message.service';
import { Message } from '../../models/message.model';

describe('MessageListComponent', () => {
  let component: MessageListComponent;
  let fixture: ComponentFixture<MessageListComponent>;
  let mockMessageService: jasmine.SpyObj<MessageService>;
  let mockDialog: jasmine.SpyObj<MatDialog>;

  beforeEach(async () => {
    mockMessageService = jasmine.createSpyObj('MessageService', ['getMessages']);
    mockDialog = jasmine.createSpyObj('MatDialog', ['open']);

    await TestBed.configureTestingModule({
      imports: [MessageListComponent],
      providers: [
        { provide: MessageService, useValue: mockMessageService },
        { provide: MatDialog, useValue: mockDialog }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MessageListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load messages on initialization', () => {
    const mockMessages: Message[] = [
      { id: '1', timestamp: new Date(), status: 'Success', content: 'Test message 1' },
      { id: '2', timestamp: new Date(), status: 'Failed', content: 'Test message 2' }
    ];
    mockMessageService.getMessages.and.returnValue(of(mockMessages));

    component.ngOnInit();

    expect(mockMessageService.getMessages).toHaveBeenCalled();
    expect(component.messages).toEqual(mockMessages);
  });

  it('should handle error when loading messages', () => {
    spyOn(console, 'error');
    mockMessageService.getMessages.and.returnValue(throwError(() => new Error('Error loading messages')));

    component.ngOnInit();

    expect(mockMessageService.getMessages).toHaveBeenCalled();
    expect(console.error).toHaveBeenCalledWith('Error loading messages', jasmine.any(Error));
  });

  it('should open message detail dialog', () => {
    const mockMessage: Message = { id: '1', timestamp: new Date(), status: 'Success', content: 'Test message' };

    component.openMessageDetail(mockMessage);

    expect(mockDialog.open).toHaveBeenCalledWith(jasmine.any(Function), {
      width: '600px',
      data: mockMessage
    });
  });
});
