import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSelfCareBoxDetailComponent } from './view-selfCareBox-detail.component';

describe('ViewSelfCareBoxDetailComponent', () => {
  let component: ViewSelfCareBoxDetailComponent;
  let fixture: ComponentFixture<ViewSelfCareBoxDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewSelfCareBoxDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewSelfCareBoxDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});