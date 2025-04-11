import { Categoria } from './categoria.model';

export interface Produto {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  quantidadeEstoque: number;
  categoria: Categoria;
}

export interface ProdutoDTO {
  nome: string;
  descricao: string;
  preco: number;
  quantidadeEstoque: number;
  categoriaId: number;
}
