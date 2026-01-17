import { Component } from '@angular/core';
import { HomeComponent } from './home/home.component';  // path sahi hona chahiye

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HomeComponent],  // ✅ HomeComponent add karo
  templateUrl: './app.component.html'
})
export class AppComponent {}

