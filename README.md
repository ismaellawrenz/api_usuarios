# API de Usuários

API REST para gerenciamento de usuários, construída com Spring Boot, H2 Database e Java 21.

---
## Funcionalidades

- CRUD de usuários (Create, Read, Update, Delete)
- Validação de dados (nome e e-mail obrigatórios)
- Paginação de usuários
- Testes unitários para serviço

---
## Endpoints principais

| Método | Endpoint                                                      | Descrição                 |
|--------|-----------------|-------------------------------------------------------------------------|
| POST   | /user                                                        | Cria um usuário            |
| GET    | /user/{id}                                                   | Retorna um usuário por ID  |
| PUT    | /user/{id}                                                   | Atualiza um usuário        |
| DELETE | /user/{id}                                                   | Remove um usuário          |
| GET    | /user/page?page=0&size=10&sortBy=id&sortDirection=desc       | Lista usuários paginados   |


## Rodando a aplicação com Docker Compose

1. Clone este repositório:

```bash
git clone  https://github.com/ismaellawrenz/api_usuarios.git
cd api_usuarios
```
2. Build da imagem Docker e execução da aplicação:
```bash
docker compose up --build
```
3. A aplicação ficará disponível em: http://localhost:8080
