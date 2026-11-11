# Lanchonete — Java com MVC e padrões de projeto

Aplicação de console para simular uma lanchonete. O cliente pode consultar o cardápio, criar pedidos, adicionar e remover itens, consultar valores e cancelar pedidos dentro das regras de negócio. Os dados são **mockados e mantidos em memória**: ao encerrar a aplicação, os pedidos são perdidos.

## Tecnologias

- Java 17 ou superior
- Programação Orientada a Objetos
- Collections e Streams da API Java
- Aplicação de console (entrada pelo teclado)

## Arquitetura MVC

O projeto separa responsabilidades em três camadas:

- **Model (`model/`)**: entidades, regras de negócio, cardápio, lanches, status e observadores.
- **View (`view/`)**: menu de console, leitura do teclado e apresentação dos dados.
- **Controller (`controller/`)**: recebe as ações da View e encaminha para a camada de serviço.

Camadas de apoio:
- **Service (`service/`)**: `LanchoneteFacade`, que concentra os casos de uso.
- **Repository (`repository/`)**: contrato e implementação em memória para persistência simulada.
- **Exceptions (`model/exceptions/`)**: exceções específicas do domínio.

Fluxo simplificado:

```text
Cliente
   |
   v
ConsoleView (View)
   |
   v
LanchoneteController (Controller)
   |
   v
LanchoneteFacade (Service)
   |                  |
   v                  v
Model             RepositorioPedidos
                      |
                      v
             RepositorioPedidosEmMemoria
```

## Padrões de projeto utilizados

| Padrão | Onde está aplicado | Responsabilidade |
|---|---|---|
| Singleton | `model/Cardapio.java` | Mantém uma única instância do cardápio. |
| Prototype | `model/Lanche.java` e classes concretas | Cria cópias independentes dos lanches-base. |
| Decorator | `AdicionalDecorator`, `Queijo`, `Bacon`, `BatataPalha` | Acrescenta adicionais e seus preços sem alterar as classes-base. |
| Facade | `service/LanchoneteFacade.java` | Simplifica o acesso aos casos de uso. |
| Observer | `PedidoObserver`, `Cozinha`, `TelaClienteObserver` | Notifica interessados quando o status do pedido muda. |
| Repository | `repository/RepositorioPedidos.java` | Abstrai o armazenamento dos pedidos. |
| Injeção de dependência | `Main.java` e construtores | Conecta as dependências sem instanciá-las dentro dos consumidores. |
| MVC | `view/`, `controller/`, `model/` | Separa interface, fluxo de entrada e regras de negócio. |

## Funcionalidades

1. Consultar modelos e adicionais disponíveis.
2. Criar pedido com nome do cliente, modelo e adicionais.
3. Listar todos os pedidos.
4. Consultar detalhes, itens, status e total.
5. Adicionar lanches a um pedido.
6. Remover itens de um pedido.
7. Cancelar pedido.
8. Simular avanço do preparo para demonstrar o Observer.

### Regras de negócio

- Um pedido começa com status `RECEBIDO`.
- O cliente pode adicionar ou remover itens enquanto o pedido estiver `RECEBIDO`.
- O pedido só pode ser cancelado enquanto estiver `RECEBIDO`.
- O pedido precisa ter ao menos um item.
- A opção de simular o avanço do preparo é uma funcionalidade demonstrativa; em um sistema real, essa ação seria restrita à cozinha ou a um perfil administrativo.
- Os status avançam por `RECEBIDO → EM_PREPARACAO → PRONTO → ENTREGUE`.

## Cardápio e dados mockados

### Lanches

| Modelo | Preço |
|---|---:|
| Tradicional | R$ 10,00 |
| Duplo | R$ 15,00 |

### Adicionais

| Adicional | Preço |
|---|---:|
| Queijo | R$ 3,00 |
| Bacon | R$ 4,50 |
| Batata palha | R$ 2,00 |

Na inicialização são criados dois pedidos de demonstração: Ana (duplo com queijo, bacon e batata) e Bruno (tradicional com queijo).

## Como executar

### Pela IDE (IntelliJ IDEA)

1. Abra a pasta do projeto.
2. Configure um JDK 17 ou superior.
3. Marque `src` como Source Root, caso a IDE não reconheça automaticamente.
4. Execute a classe `Main`.

### Pelo terminal

Na raiz do projeto, compile:

```bash
javac -d out $(find src -name "*.java")
```

Execute:

```bash
java -cp out Main
```

No Windows PowerShell, uma alternativa para compilar é:

```powershell
$files = Get-ChildItem -Path src -Recurse -Filter *.java
javac -d out $files.FullName
java -cp out Main
```

## Limitações atuais

- Não existe banco de dados: o repositório é em memória.
- Não existe autenticação; o nome digitado identifica o cliente apenas para a demonstração.
- A listagem apresenta todos os pedidos, pois ainda não há separação por conta de cliente.
- O avanço do status está exposto no menu apenas para simular o trabalho da cozinha.
- Não há interface gráfica ou API REST.

## Próximas evoluções possíveis

- Persistência com banco de dados.
- Autenticação e consulta de pedidos por cliente.
- Separação de permissões entre cliente e cozinha.
- Testes unitários com JUnit.
- API REST com Spring Boot.
- Histórico de alterações do pedido.

## Estrutura de pastas

```text
lanchonete-mvc/
├── README.md
└── src/
    ├── Main.java
    ├── controller/
    │   └── LanchoneteController.java
    ├── model/
    │   ├── AdicionalDecorator.java
    │   ├── Bacon.java
    │   ├── BatataPalha.java
    │   ├── CachorroQuente.java
    │   ├── CachorroQuenteDuplo.java
    │   ├── CachorroQuenteTradicional.java
    │   ├── Cardapio.java
    │   ├── Cozinha.java
    │   ├── Lanche.java
    │   ├── Pedido.java
    │   ├── PedidoObserver.java
    │   ├── Queijo.java
    │   ├── StatusPedido.java
    │   ├── TelaClienteObserver.java
    │   └── exceptions/
    ├── repository/
    ├── service/
    └── view/
```
