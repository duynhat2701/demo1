import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe } from '../shared/pipes/CurrencyPipe.pipe';
import { NgFor, NgIf } from '@angular/common';
import { Product, ProductPayload } from '../products/product.model';
import { ProductService } from '../products/product.service';

@Component({
  selector: 'app-home',
  imports: [CurrencyPipe, NgFor, NgIf, RouterLink, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  private readonly productService = inject(ProductService);
  private readonly defaultImage = 'assets/images/tra sua.jpg';

  products: Product[] = [];
  isModalOpen = false;
  isEditMode = false;
  editingProductId: number | null = null;
  errorMessage = '';
  productForm: ProductPayload = this.createEmptyForm();

  constructor() {
    this.loadProducts();
  }

  protected openCreateModal(): void {
    this.isModalOpen = true;
    this.isEditMode = false;
    this.editingProductId = null;
    this.errorMessage = '';
    this.productForm = this.createEmptyForm();
  }

  protected openEditModal(product: Product): void {
    this.isModalOpen = true;
    this.isEditMode = true;
    this.editingProductId = product.id;
    this.errorMessage = '';
    this.productForm = {
      name: product.name,
      price: product.price,
      image: product.image,
    };
  }

  protected closeModal(): void {
    this.isModalOpen = false;
    this.isEditMode = false;
    this.editingProductId = null;
    this.errorMessage = '';
    this.productForm = this.createEmptyForm();
  }

  protected submitForm(): void {
    if (!this.productForm.name.trim() || this.productForm.price <= 0 || !this.productForm.image.trim()) {
      this.errorMessage = 'Vui long nhap day du ten, gia va hinh anh hop le.';
      return;
    }

    const payload: ProductPayload = {
      name: this.productForm.name.trim(),
      price: Number(this.productForm.price),
      image: this.productForm.image.trim(),
    };

    if (this.isEditMode && this.editingProductId !== null) {
      const updatedProduct = this.productService.updateProduct(this.editingProductId, payload);

      if (!updatedProduct) {
        this.errorMessage = 'Cap nhat that bai vi san pham khong ton tai.';
        return;
      }
    } else {
      this.productService.createProduct(payload);
    }

    this.loadProducts();
    this.closeModal();
  }

  protected deleteProduct(id: number): void {
    this.productService.deleteProduct(id);
    this.loadProducts();
  }

  private createEmptyForm(): ProductPayload {
    return {
      name: '',
      price: 0,
      image: this.defaultImage,
    };
  }

  private loadProducts(): void {
    this.products = this.productService.getProducts();
  }
}
