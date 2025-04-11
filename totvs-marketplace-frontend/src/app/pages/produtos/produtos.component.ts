import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProdutoService } from '../../core/services/produto/produto.service';
import { Produto } from '../../models/produto.model';
import { FormsModule } from '@angular/forms';
import { CategoriaService } from '../../core/services/categoria/categoria.service';

@Component({
  standalone: true,
  selector: 'app-produtos',
  imports: [CommonModule, FormsModule],
  templateUrl: './produtos.component.html',
  styleUrls: ['./produtos.component.scss'],
})
export class ProdutosComponent implements OnInit {
  produtos: Produto[] = [];
  categorias: { id: number; nome: string }[] = [];

  filtroNome = '';
  filtroCategoriaId: number | '' = '';

  edicoesAtivas: { [id: number]: boolean } = {};
  produtoEditado: { [id: number]: Produto } = {};

  mostrarFormulario = false;

  novoProduto = {
    nome: '',
    descricao: '',
    preco: 0,
    quantidadeEstoque: 0,
    categoriaId: 0,
  };

  constructor(
    private produtoService: ProdutoService,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit(): void {
    this.buscarProdutos();
    this.categoriaService.listarCategorias().subscribe((categorias) => {
      this.categorias = categorias;
    });
  }

  criarProduto() {
    const { nome, descricao, preco, quantidadeEstoque, categoriaId } =
      this.novoProduto;

    if (
      !nome ||
      !descricao ||
      preco <= 0 ||
      quantidadeEstoque < 0 ||
      categoriaId === 0
    ) {
      alert(
        'Preencha todos os campos corretamente.\nPreço deve ser maior que 0.\nCategoria é obrigatória.'
      );
      return;
    }

    const payload = {
      nome,
      descricao,
      preco,
      quantidadeEstoque,
      categoriaId,
    };

    this.produtoService.cadastrar(payload).subscribe(() => {
      this.novoProduto = {
        nome: '',
        descricao: '',
        preco: 0,
        quantidadeEstoque: 0,
        categoriaId: 0,
      };

      this.mostrarFormulario = false;
      this.buscarProdutos();
    });
  }

  buscarProdutos(): void {
    this.produtoService
      .filtrarProdutos(this.filtroNome, this.filtroCategoriaId || undefined)
      .subscribe((res) => {
        this.produtos = res.content ?? res; // suporta array e paginado
      });
  }

  editar(produto: Produto): void {
    this.edicoesAtivas[produto.id] = true;
    this.produtoEditado[produto.id] = { ...produto };
  }

  cancelarEdicao(id: number): void {
    delete this.edicoesAtivas[id];
    delete this.produtoEditado[id];
  }

  salvarEdicao(id: number): void {
    const produto = this.produtoEditado[id];

    if (!produto.categoria || produto.categoria.id == null) {
      alert('Categoria é obrigatória');
      return;
    }

    console.log(produto.categoria.id);

    const dto = {
      nome: produto.nome,
      descricao: produto.descricao,
      preco: produto.preco,
      quantidadeEstoque: produto.quantidadeEstoque,
      categoriaId: produto.categoria.id,
    };

    this.produtoService.editar(produto.id, dto).subscribe(() => {
      this.cancelarEdicao(id);
      this.buscarProdutos();
    });
  }

  excluir(id: number): void {
    const produto = this.produtos.find((p) => p.id === id);

    if (produto && produto.quantidadeEstoque > 0) {
      alert('Não é possível excluir um produto com estoque maior que 0.');
      return;
    }

    if (confirm('Tem certeza que deseja excluir este produto?')) {
      this.produtoService.deletar(id).subscribe(() => {
        this.buscarProdutos();
      });
    }
  }
}
