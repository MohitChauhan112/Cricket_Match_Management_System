import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-match-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './match-card.component.html',
  styleUrls: ['./match-card.component.css']
})
export class MatchCardComponent {

  @Input() match: any; // match object
  @Input() showStartButton: boolean = false; // only for upcoming matches

  @Output() startMatch: EventEmitter<number> = new EventEmitter();

  onStartMatch() {
    this.startMatch.emit(this.match.id);
  }
}
