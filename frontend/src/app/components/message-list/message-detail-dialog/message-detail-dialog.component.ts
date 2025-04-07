import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Message } from '../../../models/message.model';

@Component({
  selector: 'app-message-detail-dialog',
  templateUrl: './message-detail-dialog.component.html',
  styleUrls: ['./message-detail-dialog.component.scss']
})
export class MessageDetailDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public message: Message) { }
}