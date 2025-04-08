import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { PartnerFormDialogComponent } from './partner-form-dialog.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('PartnerFormDialogComponent', () => {
  let component: PartnerFormDialogComponent;
  let fixture: ComponentFixture<PartnerFormDialogComponent>;
  let mockDialogRef: jasmine.SpyObj<MatDialogRef<PartnerFormDialogComponent>>;

  beforeEach(async () => {
    mockDialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);

    await TestBed.configureTestingModule({
      imports: [PartnerFormDialogComponent, ReactiveFormsModule, NoopAnimationsModule],
      providers: [{ provide: MatDialogRef, useValue: mockDialogRef }]
    }).compileComponents();

    fixture = TestBed.createComponent(PartnerFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form with default values', () => {
    const formValues = component.partnerForm.value;
    expect(formValues).toEqual({
      alias: '',
      type: '',
      direction: '',
      application: '',
      processedFlowType: '',
      description: ''
    });
  });

  it('should close the dialog with form data on submit if the form is valid', () => {
    component.partnerForm.setValue({
      alias: 'Test Alias',
      type: 'Test Type',
      direction: 'INBOUND',
      application: 'Test Application',
      processedFlowType: 'MESSAGE',
      description: 'Test Description'
    });

    component.onSubmit();

    expect(mockDialogRef.close).toHaveBeenCalledWith({
      alias: 'Test Alias',
      type: 'Test Type',
      direction: 'INBOUND',
      application: 'Test Application',
      processedFlowType: 'MESSAGE',
      description: 'Test Description'
    });
  });

  it('should not close the dialog on submit if the form is invalid', () => {
    component.partnerForm.setValue({
      alias: '',
      type: '',
      direction: '',
      application: '',
      processedFlowType: '',
      description: ''
    });

    component.onSubmit();

    expect(mockDialogRef.close).not.toHaveBeenCalled();
  });

  it('should close the dialog without data on cancel', () => {
    component.onCancel();

    expect(mockDialogRef.close).toHaveBeenCalledWith();
  });

  it('should mark all fields as touched when submitting an invalid form', () => {
    spyOn(component.partnerForm, 'markAllAsTouched');
    component.partnerForm.setValue({
      alias: '',
      type: '',
      direction: '',
      application: '',
      processedFlowType: '',
      description: ''
    });

    component.onSubmit();

    expect(component.partnerForm.markAllAsTouched).toHaveBeenCalled();
  });
});
