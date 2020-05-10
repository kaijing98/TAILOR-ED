import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterArtworkComponent } from './filter-artwork.component';

describe('FilterArtworkComponent', () => {
  let component: FilterArtworkComponent;
  let fixture: ComponentFixture<FilterArtworkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilterArtworkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterArtworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
