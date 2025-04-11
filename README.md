# totvs-marketplace
---
# Front-end
# 📦 Sistema de Gerenciamento de Categorias e Produtos

Este projeto é uma aplicação Angular para gerenciamento de categorias de produtos. Permite visualizar, editar, excluir e adicionar novas categorias, com validações para impedir a exclusão de categorias que contenham produtos vinculados.

## 🚀 Funcionalidades

- Listagem de categorias
- Edição e salvamento de nomes de categorias
- Exclusão de categorias (bloqueada se houver produtos associados)
- Validação visual e mensagens de alerta
- Integração com API para persistência dos dados

## 🛠️ Tecnologias Utilizadas

- Angular
- TypeScript
- RxJS
- HTML/CSS

## 🧠 Lógica de Regras

- **Edição**: categorias podem ser editadas inline, com campos controlados por estado.
- **Exclusão**: antes de excluir uma categoria, a aplicação verifica se há produtos associados e exibe um `alert()` caso não seja possível excluí-la.
- **Interface `Categoria`**: possui os campos `id`, `nome` e `produtos`, onde `produtos` pode ser um array opcional retornado da API.

## 🗃️ Estrutura

```
src/
├── app/
│   ├── pages/
│   │   └── categorias/
│   │       ├── categorias.component.ts
│   │       ├── categorias.component.html
│   │       └── categorias.component.scss
│   ├── models/
│   │   └── categoria.model.ts
│   └── services/
│       └── categoria.service.ts
```

## ✅ Pré-requisitos

- Node.js
- Angular CLI

## ▶️ Como rodar

```bash
npm install
ng serve
```
- Acesse https://localhost:4200
---
# Back-end
Este é o backend da aplicação TOTVS Marketplace, desenvolvido em Java com Spring Boot. Ele oferece uma API REST para gerenciamento de produtos e categorias, e se comunica com um banco de dados PostgreSQL, ambos executados via Docker.

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- PostgreSQL
- Docker & Docker Compose

## 🚀 Funcionalidades

- CRUD de Produtos
- CRUD de Categorias (com validação para não excluir categorias com produtos vinculados)

## Estrutura de Endpoints

- `GET /api/produtos`
- `POST /api/produtos`
- `PUT /api/produtos/{id}`
- `DELETE /api/produtos/{id}`
- `GET /api/categorias`
- `POST /api/categorias`
- `PUT /api/categorias/{id}`
- `DELETE /api/categorias/{id}`
- Etc

## Executando o Projeto

## ✅ Pré-requisitos

- Docker
- Docker Compose
- Maven
- Java

## ▶️ Como rodar
1. ```bash
    # No diretório do projeto backend, execute:
    mvn -N io.takari:maven:wrapper
   ```

2. ```bash
    # No diretório do projeto backend, execute (ainda mais se fizer alguma alteração):
    ./mvnw clean install -DskipTests
   ```
3. ```bash
    # No diretório do projeto backend, execute:
    docker-compose up --build
    ```

E já pode testar as rotas em http://localhost:8080/api
