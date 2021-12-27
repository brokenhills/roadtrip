import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentWorkflowsComponent } from './recent-workflows.component';

describe('RecentWorkflowsComponent', () => {
  let component: RecentWorkflowsComponent;
  let fixture: ComponentFixture<RecentWorkflowsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecentWorkflowsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecentWorkflowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
