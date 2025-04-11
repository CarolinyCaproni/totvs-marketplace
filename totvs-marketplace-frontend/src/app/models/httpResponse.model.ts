// src/app/models/pagina.model.ts
export interface HttpResponse<T> {
  content: T;
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  empty: boolean;
}
