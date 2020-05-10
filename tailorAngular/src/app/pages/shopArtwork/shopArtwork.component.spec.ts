import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopArtworkComponent } from './shopArtwork.component';

describe('ShopArtworkComponent', () => {
  let component: ShopArtworkComponent;
  let fixture: ComponentFixture<ShopArtworkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShopArtworkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopArtworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
