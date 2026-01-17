import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatchService } from '../../services/match.service';
import { MatchCardComponent } from '../../components/match-card/match-card.component';

@Component({
  selector: 'app-upcoming',
  standalone: true,
  imports: [CommonModule, MatchCardComponent],
  templateUrl: './upcoming.component.html',
  styleUrls: ['./upcoming.component.css']
})
export class UpcomingComponent implements OnInit {

  matches: any[] = [];

  constructor(private matchService: MatchService) {}

  ngOnInit(): void {
    console.log('🔥 UpcomingComponent Loaded');

    this.matchService.getUpcomingMatches().subscribe({
      next: (data) => {
        console.log('📅 UPCOMING MATCHES:', data);
        this.matches = data;
      },
      error: (err) => console.error(err)
    });
  }
}
