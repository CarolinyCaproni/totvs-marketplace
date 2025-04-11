import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'produtos',
    loadComponent: () =>
      import('./pages/produtos/produtos.component').then(
        (m) => m.ProdutosComponent
      ),
  },
  {
    path: 'categorias',
    loadComponent: () =>
      import('./pages/categorias/categorias.component').then(
        (m) => m.CategoriasComponent
      ),
  },
];
