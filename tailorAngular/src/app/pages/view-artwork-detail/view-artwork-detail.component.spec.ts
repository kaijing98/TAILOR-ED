import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewArtworkDetailComponent } from './view-artwork-detail.component';

describe('ViewArtworkDetailComponent', () => {
  let component: ViewArtworkDetailComponent;
  let fixture: ComponentFixture<ViewArtworkDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewArtworkDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewArtworkDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
