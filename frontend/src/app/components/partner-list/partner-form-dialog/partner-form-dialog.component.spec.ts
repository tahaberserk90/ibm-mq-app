import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartnerFormDialogComponent } from './partner-form-dialog.component';

describe('PartnerFormDialogComponent', () => {
  let component: PartnerFormDialogComponent;
  let fixture: ComponentFixture<PartnerFormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartnerFormDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PartnerFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
