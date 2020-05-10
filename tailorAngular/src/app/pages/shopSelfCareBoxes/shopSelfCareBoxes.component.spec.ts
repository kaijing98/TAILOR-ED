import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopSelfCareBoxComponent } from './shopSelfCareBoxes.component';

describe('ShopSelfCareBoxComponent', () => {
  let component: ShopSelfCareBoxComponent;
  let fixture: ComponentFixture<ShopSelfCareBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShopSelfCareBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopSelfCareBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
