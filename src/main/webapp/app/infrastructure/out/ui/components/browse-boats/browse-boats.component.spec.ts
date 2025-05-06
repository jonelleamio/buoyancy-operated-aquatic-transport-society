import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseBoatsComponent } from './browse-boats.component';

describe('BrowseBoatsComponent', () => {
  let component: BrowseBoatsComponent;
  let fixture: ComponentFixture<BrowseBoatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BrowseBoatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrowseBoatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
