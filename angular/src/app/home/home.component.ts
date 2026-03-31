 import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderLayoutComponent } from '../shared/header-layout/header-layout.component';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe } from '../shared/pipes/CurrencyPipe.pipe';
import { NgClass, NgFor } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    HeaderLayoutComponent,
    FormsModule,
    CurrencyPipe,
    NgFor ,
    NgClass
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  isActive =true;

  products = [
    {
      name: 'tra sua',
      price: 40000,
      image: 'assets/images/tra sua.jpg',
    },
    {
      name: 'hgret',
      price: 30000,
      image: 'assets/images/tra sua.jpg',
    },
    {
      name: 'tfff',
      price: 70000,
      image: 'assets/images/tra sua.jpg',
    },
    {
      name: 'tbbbb',
      price: 40000,
      image: 'assets/images/tra sua.jpg',
    },
  ];
}
