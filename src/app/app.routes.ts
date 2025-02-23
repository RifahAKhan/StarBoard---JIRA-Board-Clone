import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ScrumBoardComponent } from './scrum-board/scrum-board.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'scrum-board', component: ScrumBoardComponent },
  { path: 'active-sprint', component: ScrumBoardComponent }, 
  { path: 'backlog', component: ScrumBoardComponent } 
];
