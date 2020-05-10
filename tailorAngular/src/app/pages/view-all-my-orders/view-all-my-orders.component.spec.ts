import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllMyOrdersComponent } from './view-all-my-orders.component';

describe('ViewAllMyOrdersComponent', () => {
  let component: ViewAllMyOrdersComponent;
  let fixture: ComponentFixture<ViewAllMyOrdersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllMyOrdersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllMyOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
