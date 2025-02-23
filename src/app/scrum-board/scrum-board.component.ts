import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../sidebar/sidebar.component';

@Component({
  selector: 'app-scrum-board',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './scrum-board.component.html',
  styleUrls: ['./scrum-board.component.css']
})
export class ScrumBoardComponent {}
