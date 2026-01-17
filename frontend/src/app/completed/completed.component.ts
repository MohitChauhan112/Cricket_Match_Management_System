import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchCardComponent } from '../components/match-card/match-card.component';
import { MatchService } from '../services/match.service';
@Component({
  selector: 'app-completed',
  standalone: true,
  imports: [CommonModule, MatchCardComponent],
  templateUrl: './completed.component.html',
  styleUrls: ['./completed.component.css']
})
export class CompletedComponent implements OnInit {

  matches: any[] = [];

  constructor(private matchService: MatchService) {}

  ngOnInit(): void {
    console.log('🔥 CompletedComponent Loaded');
    this.loadCompletedMatches();
  }

  loadCompletedMatches() {
    this.matchService.getCompletedMatches().subscribe({
      next: (data) => {
        console.log('✅ COMPLETED MATCHES:', data);
        this.matches = data;
      },
      error: (err) => {
        console.error('❌ ERROR:', err);
      }
    });
  }
}
