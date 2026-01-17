import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { MatchCardComponent } from '../components/match-card/match-card.component';
import { MatchService } from '../services/match.service';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, MatchCardComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  showSection: string = 'live'; // default section

  upcomingMatches: any[] = [];
  liveMatches: any[] = [];
  completedMatches: any[] = [];

  private livePollSubscription!: Subscription;

  constructor(
    private matchService: MatchService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit() {
    // Only run backend calls in browser
    if (isPlatformBrowser(this.platformId)) {
      this.loadMatches();
      this.pollLiveMatches();
    }
  }

  ngOnDestroy() {
    // Unsubscribe interval to avoid memory leaks
    if (this.livePollSubscription) {
      this.livePollSubscription.unsubscribe();
    }
  }

  // Load matches from backend
  loadMatches() {
    this.matchService.getUpcomingMatches().subscribe(data => this.upcomingMatches = data);
    this.matchService.getLiveMatches().subscribe(data => this.liveMatches = data);
    this.matchService.getCompletedMatches().subscribe(data => this.completedMatches = data);
  }

  // Start match → UPCOMING → LIVE
  startUpcomingMatch(matchId: number) {
  this.matchService.startMatch(matchId).subscribe({
    next: (updatedMatch) => {
      // Move match from upcoming → live
      this.liveMatches = [...this.liveMatches, updatedMatch];
      this.upcomingMatches = this.upcomingMatches.filter(m => m.id !== matchId);
    },
    error: (err) => console.error('Error starting match:', err)
  });
}



  // Poll live matches every 2s + move completed automatically
  pollLiveMatches() {
    this.livePollSubscription = interval(2000).subscribe(() => {
      this.matchService.getLiveMatches().subscribe(data => {
        this.liveMatches = data;

        // Move completed matches
        const nowCompleted = this.liveMatches.filter(m => m.status === 'COMPLETED');
        nowCompleted.forEach(match => {
          this.liveMatches = this.liveMatches.filter(m => m !== match);
          this.completedMatches.push(match);
        });
      });
    });
  }

  // Optional: highlight active filter button
  isActive(section: string) {
    return this.showSection === section;
  }
}
