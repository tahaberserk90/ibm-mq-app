import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { Direction, ProcessedFlowType, Partner } from '../../../models/partner.model';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-partner-form-dialog',
  templateUrl: './partner-form-dialog.component.html',
  styleUrls: ['./partner-form-dialog.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ]
})
export class PartnerFormDialogComponent {
  partnerForm: FormGroup;
  directions: Direction[] = ['INBOUND', 'OUTBOUND'];
  flowTypes: ProcessedFlowType[] = ['MESSAGE', 'ALERTING', 'NOTIFICATION'];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<PartnerFormDialogComponent>
  ) {
    this.partnerForm = this.fb.group({
      alias: ['', Validators.required],
      type: ['', Validators.required],
      direction: ['', Validators.required],
      application: [''],
      processedFlowType: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.partnerForm.valid) {
      this.dialogRef.close(this.partnerForm.value);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}