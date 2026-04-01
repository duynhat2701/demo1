import { Component, inject } from '@angular/core';
import { NgIf } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CurrencyPipe } from '../shared/pipes/CurrencyPipe.pipe';
import { Product } from '../products/product.model';
import { ProductService } from '../products/product.service';

@Component({
  selector: 'app-detail',
  imports: [NgIf, RouterLink, CurrencyPipe],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css',
})
export class DetailComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly productService = inject(ProductService);

  protected product?: Product;

  constructor() {
    const routeId = this.route.snapshot.paramMap.get('id');
    const productId = routeId ? Number(routeId) : NaN;

    if (!Number.isNaN(productId)) {
      this.product = this.productService.getProductById(productId);
    }
  }
}
