<div align="center">

# 📚 Online Book Store

**Uma livraria online para descobrir livros e comprar com facilidade.**

Navegue pelo catálogo, adicione livros ao carrinho e realize compras.  
Na área administrativa, gerencie livros e pedidos e acompanhe estatísticas de vendas.

</div>

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| ☕ **Java · Spring Boot** | Aplicação |
| 🗄️ **MySQL** | Banco de dados |
| 🎨 **Thymeleaf** | Interface web |
| 🔐 **Spring Security** | Segurança |
| 🔄 **Hibernate ORM** | Persistência |

## 🧩 Arquitetura

A aplicação utiliza **arquitetura hexagonal** para separar as regras de negócio das demais partes do sistema, facilitando a manutenção e os testes.

| Componente | Responsabilidade |
|---|---|
| **Ports (portas)** | Definem os contratos de comunicação com a lógica de negócio. |
| **Adapters (adaptadores)** | Implementam esses contratos, por exemplo, nos controladores web e repositórios. |

<p align="center">
  <img src="docs/img.png" alt="Diagrama da arquitetura hexagonal" width="560"/>
</p>

## 🗂️ Estrutura do projeto

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
