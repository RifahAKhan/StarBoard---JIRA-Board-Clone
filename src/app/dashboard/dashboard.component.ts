import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  projects = [
    { name: 'Scrum Board', description: 'Agile Workflow' },
    { name: 'Bug Tracker', description: 'Issue Management' },
    { name: 'Feature Development', description: 'Software Development' },
    { name: 'Marketing Campaign', description: 'Business Strategy' }
  ];

  constructor(private router: Router) {}

  openScrumBoard() {
    this.router.navigate(['/scrum-board']);
  }
}
