# Lanchonete — Java com MVC e padrões de projeto

Aplicação de console para simular uma lanchonete. O cliente pode consultar o cardápio, criar pedidos, adicionar e remover itens, consultar valores e cancelar pedidos dentro das regras de negócio. Os dados são **mockados e mantidos em memória**: ao encerrar a aplicação, os pedidos são perdidos.

## Tecnologias

- Java 17 ou superior (o código usa *switch expressions* e `String.isBlank()`)
- Programação Orientada a Objetos
- Collections, Streams, lambdas e *method references* da API Java
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

## Padrões de projeto GoF

O projeto aplica **3 dos 4 padrões vistos em sala** (Observer, Decorator e Prototype) e, como padrões adicionais do livro *Design Patterns* (GoF), **Singleton** e **Facade**. O Abstract Factory não foi utilizado.

| Padrão GoF | Categoria | Onde está aplicado | Responsabilidade |
|---|---|---|---|
| Observer | Comportamental | `PedidoObserver`, `Pedido`, `Cozinha`, `TelaClienteObserver` | Notifica a cozinha e o cliente quando o status do pedido muda. |
| Decorator | Estrutural | `AdicionalDecorator`, `Queijo`, `Bacon`, `BatataPalha` | Acrescenta adicionais e seus preços sem alterar as classes-base. |
| Prototype | Criacional | `Lanche.clonar()`, classes concretas e `Cardapio.novoLanche()` | Cria cópias independentes dos lanches-modelo registrados no cardápio. |
| Singleton (adicional) | Criacional | `model/Cardapio.java` | Mantém uma única instância do cardápio. |
| Facade (adicional) | Estrutural | `service/LanchoneteFacade.java` | Oferece uma interface única para os casos de uso. |

### Participantes de cada padrão

| Padrão | Participantes GoF → classes do projeto |
|---|---|
| Observer | Subject/ConcreteSubject → `Pedido` · Observer → `PedidoObserver` · ConcreteObserver → `Cozinha`, `TelaClienteObserver` |
| Decorator | Component → `Lanche` · ConcreteComponent → `CachorroQuenteTradicional`, `CachorroQuenteDuplo` · Decorator → `AdicionalDecorator` · ConcreteDecorator → `Queijo`, `Bacon`, `BatataPalha` |
| Prototype | Prototype → `Lanche` · ConcretePrototype → lanches base e decorators · Client → `Cardapio.novoLanche()` · registro de protótipos → mapa `modelos` do `Cardapio` |
| Singleton | Singleton → `Cardapio` (construtor privado + `getInstance()`) |
| Facade | Facade → `LanchoneteFacade` · subsistema → `Cardapio`, `Pedido`, `RepositorioPedidos`, observadores |

### Outras práticas de arquitetura (não são padrões GoF)

| Prática | Onde está aplicada | Responsabilidade |
|---|---|---|
| MVC | `view/`, `controller/`, `model/` | Separa interface, fluxo de entrada e regras de negócio. |
| Repository | `repository/RepositorioPedidos.java` | Abstrai o armazenamento dos pedidos. |
| Injeção de dependência | `Main.java` e construtores | Conecta as dependências sem instanciá-las dentro dos consumidores. |

## Funcionalidades

Os números correspondem às opções do menu.

1. Consultar modelos e adicionais disponíveis.
2. Criar pedido com nome do cliente, modelo e adicionais.
3. Listar todos os pedidos.
4. Consultar detalhes, itens, status e total.
5. Adicionar lanches a um pedido.
6. Remover itens de um pedido.
7. Cancelar pedido.
8. Simular avanço do preparo para demonstrar o Observer.

A opção `0` encerra o programa.

### Regras de negócio

- Um pedido começa com status `RECEBIDO`.
- O cliente pode adicionar ou remover itens enquanto o pedido estiver `RECEBIDO`.
- O pedido só pode ser cancelado enquanto estiver `RECEBIDO`.
- O pedido precisa ter ao menos um item.
- Os status avançam por `RECEBIDO → EM_PREPARACAO → PRONTO → ENTREGUE`. Pedidos `ENTREGUE` ou `CANCELADO` não avançam.
- Avançar ou cancelar um pedido notifica a cozinha e o cliente (Observer).
- A opção de simular o avanço do preparo é uma funcionalidade demonstrativa; em um sistema real, essa ação seria restrita à cozinha ou a um perfil administrativo.

## Cardápio e dados mockados

Os nomes podem ser digitados com letras maiúsculas ou minúsculas.

### Lanches

| Modelo | Nome para digitar | Preço |
|---|---|---:|
| Cachorro-quente tradicional | `tradicional` | R$ 10,00 |
| Cachorro-quente duplo (2 salsichas) | `duplo` | R$ 15,00 |

### Adicionais

Digite os adicionais separados por vírgula (ex.: `queijo, bacon`) ou pressione Enter para nenhum.

| Adicional | Nome para digitar | Preço |
|---|---|---:|
| Queijo | `queijo` | R$ 3,00 |
| Bacon | `bacon` | R$ 4,50 |
| Batata palha | `batata` | R$ 2,00 |

Na inicialização são criados dois pedidos de demonstração:

| Pedido | Cliente | Itens | Total |
|---|---|---|---:|
| #1 | Ana | Duplo + queijo + bacon + batata palha | R$ 24,50 |
| #2 | Bruno | Tradicional + queijo | R$ 13,00 |

## Como executar

O código-fonte fica em `lanchonete/src`.

### Pela IDE (IntelliJ IDEA)

1. Abra a pasta `lanchonete`.
2. Configure um JDK 17 ou superior.
3. Marque `src` como Source Root, caso a IDE não reconheça automaticamente.
4. Execute a classe `Main`.

### Pelo VS Code

1. Instale o *Extension Pack for Java* e um JDK 17 ou superior.
2. Abra a pasta `lanchonete`.
3. Abra `src/Main.java` e clique em **Run** acima do método `main`.

### Pelo terminal

Dentro da pasta `lanchonete`, compile:

```bash
javac -d out $(find src -name "*.java")
```

Execute:

```bash
java -cp out Main
```

No Windows PowerShell, uma alternativa para compilar e executar é:

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
Lachonete-Padroes-Projeto/
├── README.md
└── lanchonete/
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
        │       ├── AdicionalInvalidoException.java
        │       ├── ModeloInexistenteException.java
        │       ├── PedidoNaoEncontradoException.java
        │       └── StatusInvalidoException.java
        ├── repository/
        │   ├── RepositorioPedidos.java
        │   └── RepositorioPedidosEmMemoria.java
        ├── service/
        │   └── LanchoneteFacade.java
        └── view/
            └── ConsoleView.java
```
