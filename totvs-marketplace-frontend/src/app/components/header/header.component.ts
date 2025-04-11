import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  constructor(private router: Router, private location: Location) {}

  get exibirSetaVoltar(): boolean {
    return this.router.url !== '/';
  }

  voltar(): void {
    this.location.back();
  }
}
