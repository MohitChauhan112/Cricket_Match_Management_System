import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchCardComponent } from '../components/match-card/match-card.component';
import { MatchService } from '../services/match.service';
@Component({
  selector: 'app-live',
  standalone: true,
  imports: [CommonModule, MatchCardComponent],
  templateUrl: './live.component.html',
  styleUrls: ['./live.component.css']
})
export class LiveComponent implements OnInit {

  matches: any[] = [];

  constructor(private matchService: MatchService) {}

  ngOnInit(): void {
    console.log('🔥 LiveComponent Loaded');
    this.loadLiveMatches();
  }

  loadLiveMatches() {
    this.matchService.getLiveMatches().subscribe({
      next: (data) => {
        console.log('🔥 LIVE MATCHES:', data);
        this.matches = data;
      },
      error: (err) => console.error('❌ ERROR:', err)
    });
  }
}
