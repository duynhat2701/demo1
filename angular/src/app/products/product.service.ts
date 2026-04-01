import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Product, ProductPayload } from './product.model';

const STORAGE_KEY = 'products';

const DEFAULT_PRODUCTS: Product[] = [
  {
    id: 1,
    name: 'Tra sua truyen thong',
    price: 40000,
    image: 'assets/images/tra sua.jpg',
  },
  {
    id: 2,
    name: 'Tra sua matcha',
    price: 30000,
    image: 'assets/images/tra sua.jpg',
  },
  {
    id: 3,
    name: 'Tra sua socola',
    price: 70000,
    image: 'assets/images/tra sua.jpg',
  },
  {
    id: 4,
    name: 'Tra sua duong den',
    price: 45000,
    image: 'assets/images/tra sua.jpg',
  },
];

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly platformId = inject(PLATFORM_ID);
  private products: Product[] = this.loadProducts();

  getProducts(): Product[] {
    return [...this.products];
  }

  getProductById(id: number): Product | undefined {
    return this.products.find((product) => product.id === id);
  }

  createProduct(payload: ProductPayload): Product {
    const newProduct: Product = {
      id: this.getNextId(),
      ...payload,
    };

    this.products = [...this.products, newProduct];
    this.persistProducts();
    return newProduct;
  }

  updateProduct(id: number, payload: ProductPayload): Product | undefined {
    const existingProduct = this.getProductById(id);

    if (!existingProduct) {
      return undefined;
    }

    const updatedProduct: Product = {
      ...existingProduct,
      ...payload,
      id,
    };

    this.products = this.products.map((product) =>
      product.id === id ? updatedProduct : product,
    );
    this.persistProducts();
    return updatedProduct;
  }

  deleteProduct(id: number): void {
    this.products = this.products.filter((product) => product.id !== id);
    this.persistProducts();
  }

  private getNextId(): number {
    return this.products.reduce((maxId, product) => Math.max(maxId, product.id), 0) + 1;
  }

  private loadProducts(): Product[] {
    if (!isPlatformBrowser(this.platformId)) {
      return DEFAULT_PRODUCTS;
    }

    const rawProducts = localStorage.getItem(STORAGE_KEY);

    if (!rawProducts) {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(DEFAULT_PRODUCTS));
      return DEFAULT_PRODUCTS;
    }

    try {
      const parsedProducts = JSON.parse(rawProducts) as Product[];
      if (Array.isArray(parsedProducts) && parsedProducts.length > 0) {
        return parsedProducts;
      }

      localStorage.setItem(STORAGE_KEY, JSON.stringify(DEFAULT_PRODUCTS));
      return DEFAULT_PRODUCTS;
    } catch {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(DEFAULT_PRODUCTS));
      return DEFAULT_PRODUCTS;
    }
  }

  private persistProducts(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.products));
  }
}
