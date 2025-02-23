import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

import { DashboardComponent } from './dashboard/dashboard.component';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter([{ path: '', component: DashboardComponent }]),
    provideAnimations(),
    importProvidersFrom(
      MatToolbarModule,  // ✅ Fix for 'mat-toolbar' error
      MatIconModule,
      MatTableModule,
      MatFormFieldModule,
      MatInputModule,
      MatCardModule,
      MatButtonModule
    )
  ]
};

