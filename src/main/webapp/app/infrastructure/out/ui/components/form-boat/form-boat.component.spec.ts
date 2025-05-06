import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormBoatComponent } from './form-boat.component';

describe('FormBoatComponent', () => {
  let component: FormBoatComponent;
  let fixture: ComponentFixture<FormBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormBoatComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
