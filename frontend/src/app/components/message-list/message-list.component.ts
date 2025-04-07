import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { Message } from '../../models/message.model';
import { MatDialog } from '@angular/material/dialog';
import { MessageDetailDialogComponent } from './message-detail-dialog/message-detail-dialog.component';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss']
})
export class MessageListComponent implements OnInit {
  messages: Message[] = [];
  displayedColumns: string[] = ['id', 'timestamp', 'status', 'actions'];

  constructor(
    private messageService: MessageService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(): void {
    this.messageService.getMessages().subscribe(
      messages => this.messages = messages,
      error => console.error('Error loading messages', error)
    );
  }

  openMessageDetail(message: Message): void {
    this.dialog.open(MessageDetailDialogComponent, {
      width: '600px',
      data: message
    });
  }
}