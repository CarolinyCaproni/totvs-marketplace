import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoriaService } from '../../core/services/categoria/categoria.service';
import { Categoria } from '../../models/categoria.model';

@Component({
  standalone: true,
  selector: 'app-categorias',
  imports: [CommonModule, FormsModule],
  templateUrl: './categorias.component.html',
  styleUrls: ['./categorias.component.scss'],
})
export class CategoriasComponent implements OnInit {
  categorias: Categoria[] = [];
  novaCategoria: string = '';
  edicoesAtivas: { [id: number]: boolean } = {};
  categoriaEditada: { [id: number]: string } = {};

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.buscarCategorias();
  }

  buscarCategorias(): void {
    this.categoriaService.listarCategorias().subscribe((res) => {
      this.categorias = res;
    });
  }

  criarCategoria(): void {
    if (!this.novaCategoria.trim()) {
      alert('Nome da categoria não pode estar vazio.');
      return;
    }

    this.categoriaService
      .cadastrar({ nome: this.novaCategoria })
      .subscribe(() => {
        this.novaCategoria = '';
        this.buscarCategorias();
      });
  }

  editar(categoria: Categoria): void {
    this.edicoesAtivas[categoria.id] = true;
    this.categoriaEditada[categoria.id] = categoria.nome;
  }

  cancelarEdicao(id: number): void {
    delete this.edicoesAtivas[id];
    delete this.categoriaEditada[id];
  }

  salvarEdicao(id: number): void {
    const novoNome = this.categoriaEditada[id];
    if (!novoNome.trim()) {
      alert('Nome não pode estar vazio.');
      return;
    }

    this.categoriaService.editar(id, { nome: novoNome }).subscribe(() => {
      this.cancelarEdicao(id);
      this.buscarCategorias();
    });
  }

  excluir(id: number) {
    const categoria = this.categorias.find((cat) => cat.id === id);

    if (!categoria) {
      alert('Categoria não encontrada.');
      return;
    }

    if (categoria.produtos && categoria.produtos.length > 0) {
      alert('Não é possível excluir uma categoria que possui produtos.');
      return;
    }

    this.categoriaService.excluir(id).subscribe(() => {
      this.categorias = this.categorias.filter((cat) => cat.id !== id);
    });
  }
}
