import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MessageDetailDialogComponent } from './message-detail-dialog.component';
import { Message } from '../../../models/message.model';

describe('MessageDetailDialogComponent', () => {
  let component: MessageDetailDialogComponent;
  let fixture: ComponentFixture<MessageDetailDialogComponent>;
  const mockMessage: Message = {
    id: '1',
    timestamp: new Date(),
    status: 'Success',
    content: 'This is a test message'
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MessageDetailDialogComponent],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: mockMessage }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MessageDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the message content', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain(mockMessage.content);
  });

  it('should display the message status', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain(mockMessage.status);
  });

  it('should display the message timestamp', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain(mockMessage.timestamp.toLocaleString());
  });
});
