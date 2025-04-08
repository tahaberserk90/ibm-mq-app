import { Component, Inject, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { Message } from '../../../models/message.model';

@Component({
  selector: 'app-message-detail-dialog',
  templateUrl: './message-detail-dialog.component.html',
  styleUrls: ['./message-detail-dialog.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule
  ]
})
export class MessageDetailDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public message: Message) { }
}