export interface Product {
  id: number;
  name: string;
  price: number;
  image: string;
}

export type ProductPayload = Omit<Product, 'id'>;
