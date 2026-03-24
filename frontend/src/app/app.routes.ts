import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/projects', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./pages/auth/login/login')
      .then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./pages/auth/register/register')
      .then(m => m.Register)
  },
  {
    path: 'projects',
    loadComponent: () => import('./pages/projects/project-list/project-list')
      .then(m => m.ProjectList)
  },
  {
    path: 'projects/:id',
    loadComponent: () => import('./pages/projects/project-detail/project-detail')
      .then(m => m.ProjectDetail),
    canActivate: [authGuard]
  },
  {
    path: 'my-applications',
    loadComponent: () => import('./pages/applications/my-applications/my-applications')
      .then(m => m.MyApplications),
    canActivate: [authGuard]
  },
  {
    path: 'my-contracts',
    loadComponent: () => import('./pages/contracts/my-contracts/my-contracts')
      .then(m => m.MyContracts),
    canActivate: [authGuard]
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./pages/dashboard/dashboard')
      .then(m => m.Dashboard),
    canActivate: [authGuard]
  },
  { path: '**', redirectTo: '/projects' }
];
