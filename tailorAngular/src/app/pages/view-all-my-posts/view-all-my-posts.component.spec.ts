import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllMyPostsComponent } from './view-all-my-posts.component';

describe('ViewAllMyPostsComponent', () => {
  let component: ViewAllMyPostsComponent;
  let fixture: ComponentFixture<ViewAllMyPostsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllMyPostsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllMyPostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
