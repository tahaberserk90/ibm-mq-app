import { Component } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { PartnerFormDialogComponent } from './partner-form-dialog/partner-form-dialog.component';
import { MatTableModule } from '@angular/material/table';
import { PartnerService } from '../../services/partner.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Partner } from '../../models/partner.model';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-partner-list',
  templateUrl: './partner-list.component.html',
  styleUrls: ['./partner-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatTableModule,
    MatDialogModule, // Add this
    MatSnackBarModule,
    HttpClientModule
  ],
  providers: [PartnerService]
})
export class PartnerListComponent {
  partners: Partner[] = [];
  displayedColumns: string[] = ['alias', 'type', 'direction', 'application', 'processedFlowType', 'description', 'actions'];

  constructor(
    private partnerService: PartnerService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }
  ngOnInit(): void {
    this.loadPartners();
  }
  loadPartners(): void {
    this.partnerService.getPartners().subscribe(
      partners => this.partners = partners,
      error => console.error('Error loading partners', error)
    );
  }

  openAddPartnerDialog(): void {
    const dialogRef = this.dialog.open(PartnerFormDialogComponent, {
      width: '600px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.partnerService.addPartner(result).subscribe(
          () => {
            this.loadPartners();
            this.snackBar.open('Partner added successfully', 'Close', { duration: 3000 });
          },
          error => {
            console.error('Error adding partner', error);
            this.snackBar.open('Error adding partner', 'Close', { duration: 3000 });
          }
        );
      }
    });
  }

  deletePartner(id: string): void {
    if (confirm('Are you sure you want to delete this partner?')) {
      this.partnerService.deletePartner(id).subscribe(
        () => {
          this.loadPartners();
          this.snackBar.open('Partner deleted successfully', 'Close', { duration: 3000 });
        },
        error => {
          console.error('Error deleting partner', error);
          this.snackBar.open('Error deleting partner', 'Close', { duration: 3000 });
        }
      );
    }
  }
}