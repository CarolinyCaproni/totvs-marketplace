import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Produto, ProdutoDTO } from '../../../models/produto.model';
import { HttpResponse } from '../../../models/httpResponse.model';

@Injectable({
  providedIn: 'root',
})
export class ProdutoService {
  private baseUrl = 'http://localhost:8080/api/produtos';

  constructor(private http: HttpClient) {}

  listar(): Observable<HttpResponse<Produto[]>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.get<HttpResponse<Produto[]>>(this.baseUrl, { headers });
  }

  filtrarProdutos(nome?: string, categoriaId?: number) {
    const params: any = {};

    if (nome) params.nome = nome;
    if (categoriaId) params.categoriaId = categoriaId;

    return this.http.get<HttpResponse<Produto[]>>(`${this.baseUrl}/filtro`, {
      params,
    });
  }

  cadastrar(produto: ProdutoDTO): Observable<Produto> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log('a');
    return this.http.post<Produto>(this.baseUrl, produto, { headers });
  }

  editar(id: number, dto: ProdutoDTO): Observable<Produto> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Produto>(`${this.baseUrl}/${id}`, dto, { headers });
  }

  deletar(id: number): Observable<void> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log('a');
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }
}
