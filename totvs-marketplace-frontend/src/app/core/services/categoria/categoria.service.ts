import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Categoria, CategoriaDTO } from '../../../models/categoria.model';

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {
  private baseUrl = 'http://localhost:8080/api/categorias';

  constructor(private http: HttpClient) {}

  listarCategorias() {
    return this.http.get<Categoria[]>(this.baseUrl);
  }

  cadastrar(categoria: CategoriaDTO) {
    return this.http.post<Categoria>(this.baseUrl, categoria);
  }

  editar(id: number, categoria: { nome: string }) {
    return this.http.put<Categoria>(`${this.baseUrl}/${id}`, categoria);
  }

  excluir(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
