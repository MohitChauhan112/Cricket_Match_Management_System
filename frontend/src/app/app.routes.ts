import { Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { UpcomingComponent } from './pages/upcoming/upcoming.component';
import { CompletedComponent } from './completed/completed.component';
import { LiveComponent } from './live/live.component';

export const routes: Routes = [
  { path: '', redirectTo: 'live', pathMatch: 'full' },
  { path: 'live', component: LiveComponent},
  { path: 'upcoming', component: UpcomingComponent },
  { path: 'completed', component: CompletedComponent }
];
