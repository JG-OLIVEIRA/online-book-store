<div align="center">

# Online Book Store

**Uma livraria online para descobrir livros e comprar com facilidade.**

Navegue pelo catálogo, adicione livros ao carrinho e realize compras.  
Na área administrativa, gerencie livros e pedidos e acompanhe estatísticas de vendas.

</div>

---

## Tecnologias

| Tecnologia | Uso |
|---|---|
| **Java · Spring Boot** | Aplicação |
| **MySQL** | Banco de dados |
| **Thymeleaf** | Interface web |
| **Spring Security** | Segurança |
| **Hibernate ORM** | Persistência |

## Execução local

São necessários Java 21 e Docker com Docker Compose. No PowerShell, defina senhas
locais para o usuário da aplicação e para o administrador do MySQL e inicie o banco:

```powershell
$env:MYSQL_PASSWORD = Read-Host "Senha do usuario MySQL"
$env:MYSQL_ROOT_PASSWORD = Read-Host "Senha root do MySQL"
docker compose up -d --wait
.\mvnw.cmd spring-boot:run
```

O banco usa `localhost:3306`, o banco de dados `onlinebookstore` e o usuário
`bookstore`. Opcionalmente, configure `MYSQL_PORT`, `MYSQL_DATABASE` e `MYSQL_USER`
no mesmo terminal antes de iniciar o Compose e a aplicação. Para outro servidor,
defina `DB_URL` com a URL JDBC completa.

Os dados do MySQL são mantidos no volume Docker `mysql_data`; `docker compose down`
para o container sem apagar os dados. As variáveis de inicialização do MySQL, incluindo
senhas, são aplicadas apenas quando o volume é criado pela primeira vez. O Hibernate
atualiza o esquema automaticamente para desenvolvimento local.

Os testes utilizam H2 em memória e podem ser executados sem o Docker:

```powershell
.\mvnw.cmd test
```

Os testes unitários de serviços ficam em `src/test/java/com/dev/jorge/onlinebookstore/application/service`;
os testes HTTP e de validação dos casos de uso ficam em `integration`, separados por funcionalidade.

O JaCoCo gera o relatório em `target/site/jacoco/index.html` durante `test`. Execute
`.\mvnw.cmd verify` para também exigir 100% de cobertura de linhas e ramificações
do código incluído no relatório, sem exclusões configuradas.

## Arquitetura

A aplicação utiliza **arquitetura hexagonal** para separar as regras de negócio das demais partes do sistema, facilitando a manutenção e os testes.

| Componente | Responsabilidade |
|---|---|
| **Ports (portas)** | Definem os contratos de comunicação com a lógica de negócio. |
| **Adapters (adaptadores)** | Implementam esses contratos, por exemplo, nos controladores web e repositórios. |

O núcleo (`domain` e `application`) não depende de Spring, JPA ou dos adaptadores.
Os controladores usam os casos de uso em `application/usecases`; persistência,
senhas e geração de tokens são acessadas por portas e implementadas por adaptadores.
`infrastructure/config/UseCaseConfiguration` monta as implementações.

Erros de domínio retornam JSON com o campo `error`: entrada inválida usa HTTP 400,
credenciais inválidas 401, livro não encontrado 404 e usuário duplicado 409.
JSON malformado e parâmetros HTTP ausentes ou inválidos também retornam 400.
Testes de arquitetura verificam as dependências permitidas entre o núcleo e os adaptadores.

Os campos dos records de entrada são validados com Jakarta Bean Validation na API e
nos casos de uso gerenciados pelo Spring. Título, autor, ISBN e dados de cadastro são
obrigatórios; e-mails devem ter formato válido, estoques e preços não podem ser
negativos e quantidades de pedidos devem ser positivas. `userDetails` do pedido é
opcional, mas, quando informado, seus campos também são validados. As violações
retornam HTTP 400 no campo `error`.



<p align="center">
  <img src="docs/img.png" alt="Diagrama da arquitetura hexagonal" width="560"/>
</p>



## Estrutura do projeto

```text
online-book-store/
├── src/
│   ├── main/
│   │   ├── java/com/dev/jorge/onlinebookstore/
│   │   │   ├── application/      # Lógica de negócios
│   │   │   ├── domain/           # Entidades e interfaces do domínio
│   │   │   ├── adapters/         # Adaptadores de entrada e saída
│   │   │   └── infrastructure/   # Configurações, mapeadores e segurança
│   │   └── resources/             # Configurações, templates e recursos estáticos
│   └── test
│       └── java/com/dev/jorge/onlinebookstore/ # Testes unitários e de integração
└── docs/                          # Documentação, diagramas e imagens
```
