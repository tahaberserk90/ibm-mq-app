import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MessageDetailDialogComponent } from './message-detail-dialog/message-detail-dialog.component';
import { MessageService } from '../../services/message.service';
import { Message } from '../../models/message.model';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule
  ]
})
export class MessageListComponent {
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