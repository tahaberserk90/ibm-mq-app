import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { of, throwError } from 'rxjs';

import { PartnerListComponent } from './partner-list.component';
import { PartnerService } from '../../services/partner.service';
import { Partner } from '../../models/partner.model';

describe('PartnerListComponent', () => {
  let component: PartnerListComponent;
  let fixture: ComponentFixture<PartnerListComponent>;
  let mockPartnerService: jasmine.SpyObj<PartnerService>;
  let mockDialog: jasmine.SpyObj<MatDialog>;
  let mockSnackBar: jasmine.SpyObj<MatSnackBar>;

  beforeEach(async () => {
    mockPartnerService = jasmine.createSpyObj('PartnerService', ['getPartners', 'addPartner', 'deletePartner']);
    mockDialog = jasmine.createSpyObj('MatDialog', ['open']);
    mockSnackBar = jasmine.createSpyObj('MatSnackBar', ['open']);

    await TestBed.configureTestingModule({
      imports: [PartnerListComponent],
      providers: [
        { provide: PartnerService, useValue: mockPartnerService },
        { provide: MatDialog, useValue: mockDialog },
        { provide: MatSnackBar, useValue: mockSnackBar }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PartnerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load partners on initialization', () => {
    const mockPartners: Partner[] = [{ id: '1', alias: 'Test Partner', type: 'Type1', direction: 'INBOUND', application: 'App1', processedFlowType: 'MESSAGE', description: 'Test Description' }];
    mockPartnerService.getPartners.and.returnValue(of(mockPartners));

    component.ngOnInit();

    expect(mockPartnerService.getPartners).toHaveBeenCalled();
    expect(component.partners).toEqual(mockPartners);
  });

  it('should handle error when loading partners', () => {
    spyOn(console, 'error');
    mockPartnerService.getPartners.and.returnValue(throwError(() => new Error('Error loading partners')));

    component.ngOnInit();

    expect(mockPartnerService.getPartners).toHaveBeenCalled();
    expect(console.error).toHaveBeenCalledWith('Error loading partners', jasmine.any(Error));
  });

  it('should open add partner dialog and add partner on success', () => {
    const mockPartner: Partner = {
      id: '1',
      alias: 'New Partner',
      type: 'Type1',
      direction: 'INBOUND',
      application: 'App1',
      processedFlowType: 'MESSAGE',
      description: 'Test Description'
    };
    const dialogRefSpy = { afterClosed: () => of(mockPartner) };
    mockDialog.open.and.returnValue(dialogRefSpy as any);
    mockPartnerService.addPartner.and.returnValue(of(mockPartner));
    spyOn(component, 'loadPartners');

    component.openAddPartnerDialog();

    expect(mockDialog.open).toHaveBeenCalled();
    expect(mockPartnerService.addPartner).toHaveBeenCalledWith(mockPartner);
    expect(component.loadPartners).toHaveBeenCalled();
    expect(mockSnackBar.open).toHaveBeenCalledWith('Partner added successfully', 'Close', { duration: 3000 });
  });

  it('should handle error when adding partner', () => {
    const mockPartner: Partner = {
      id: '1',
      alias: 'New Partner',
      type: 'Type1',
      direction: 'INBOUND',
      application: 'App1',
      processedFlowType: 'MESSAGE',
      description: 'Test Description'
    };
    const dialogRefSpy = { afterClosed: () => of(mockPartner) };
    mockDialog.open.and.returnValue(dialogRefSpy as any);
    mockPartnerService.addPartner.and.returnValue(throwError(() => new Error('Error adding partner')));

    component.openAddPartnerDialog();

    expect(mockDialog.open).toHaveBeenCalled();
    expect(mockPartnerService.addPartner).toHaveBeenCalledWith(mockPartner);
    expect(mockSnackBar.open).toHaveBeenCalledWith('Error adding partner', 'Close', { duration: 3000 });
  });

  it('should delete partner and reload partners on success', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    spyOn(component, 'loadPartners');
    mockPartnerService.deletePartner.and.returnValue(of(undefined));

    component.deletePartner('1');

    expect(window.confirm).toHaveBeenCalledWith('Are you sure you want to delete this partner?');
    expect(mockPartnerService.deletePartner).toHaveBeenCalledWith('1');
    expect(component.loadPartners).toHaveBeenCalled();
    expect(mockSnackBar.open).toHaveBeenCalledWith('Partner deleted successfully', 'Close', { duration: 3000 });
  });

  it('should handle error when deleting partner', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    mockPartnerService.deletePartner.and.returnValue(throwError(() => new Error('Error deleting partner')));

    component.deletePartner('1');

    expect(window.confirm).toHaveBeenCalledWith('Are you sure you want to delete this partner?');
    expect(mockPartnerService.deletePartner).toHaveBeenCalledWith('1');
    expect(mockSnackBar.open).toHaveBeenCalledWith('Error deleting partner', 'Close', { duration: 3000 });
  });
});
