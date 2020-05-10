import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterSelfCareBoxComponent } from './filter-selfCareBox.component';

describe('FilterSelfCareBoxComponent', () => {
  let component: FilterSelfCareBoxComponent;
  let fixture: ComponentFixture<FilterSelfCareBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilterSelfCareBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterSelfCareBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
