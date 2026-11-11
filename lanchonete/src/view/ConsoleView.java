package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import controller.LanchoneteController;
import model.Lanche;
import model.Pedido;
import model.exceptions.AdicionalInvalidoException;
import model.exceptions.ModeloInexistenteException;
import model.exceptions.PedidoNaoEncontradoException;
import model.exceptions.StatusInvalidoException;

/** View de terminal: toda entrada e saída do usuário fica nesta camada. */
public class ConsoleView {
    private final LanchoneteController controller;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView(LanchoneteController controller) { this.controller = controller; }

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> exibirCardapio();
                    case 2 -> criarPedido();
                    case 3 -> listarPedidos();
                    case 4 -> consultarPedido();
                    case 5 -> adicionarItem();
                    case 6 -> removerItem();
                    case 7 -> cancelarPedido();
                    case 8 -> simularProximaEtapa();
                    case 0 -> System.out.println("Até logo!");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Não foi possível realizar a operação: " + e.getMessage());
            }
            System.out.println();
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("========== LANCHONETE ==========");
        System.out.println("1 - Consultar cardápio");
        System.out.println("2 - Criar pedido");
        System.out.println("3 - Listar pedidos");
        System.out.println("4 - Consultar detalhes de um pedido");
        System.out.println("5 - Adicionar lanche a um pedido");
        System.out.println("6 - Remover item de um pedido");
        System.out.println("7 - Cancelar pedido");
        System.out.println("8 - Simular avanço do preparo (demonstração)");
        System.out.println("0 - Sair");
    }

    private void exibirCardapio() {
        System.out.println("\n--- Lanches ---");
        controller.getCardapio().getModelosDisponiveis()
                .forEach((nome, preco) -> System.out.printf("%s - R$ %.2f%n", nome, preco));
        System.out.println("--- Adicionais ---");
        controller.getCardapio().getAdicionaisDisponiveis()
                .forEach((nome, preco) -> System.out.printf("%s - R$ %.2f%n", nome, preco));
    }

    private void criarPedido() throws ModeloInexistenteException, AdicionalInvalidoException {
        String cliente = lerTexto("Nome do cliente: ");
        String modelo = lerModelo();
        List<String> adicionais = lerAdicionais();
        Pedido pedido = controller.criarPedido(cliente, modelo, adicionais);
        System.out.println("Pedido criado com sucesso! Número: #" + pedido.getId());
        exibirPedido(pedido);
    }

    private void listarPedidos() {
        List<Pedido> pedidos = controller.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }
        pedidos.forEach(p -> System.out.printf("#%d | %s | %s | R$ %.2f%n",
                p.getId(), p.getCliente(), p.getStatus(), p.getTotal()));
    }

    private void consultarPedido() throws PedidoNaoEncontradoException {
        exibirPedido(controller.buscarPedido(lerInteiro("Número do pedido: ")));
    }

    private void adicionarItem() throws PedidoNaoEncontradoException, ModeloInexistenteException, AdicionalInvalidoException {
        int id = lerInteiro("Número do pedido: ");
        controller.adicionarItem(id, lerModelo(), lerAdicionais());
        System.out.println("Item adicionado.");
        exibirPedido(controller.buscarPedido(id));
    }

    private void removerItem() throws PedidoNaoEncontradoException {
        int id = lerInteiro("Número do pedido: ");
        Pedido pedido = controller.buscarPedido(id);
        exibirPedido(pedido);
        int item = lerInteiro("Número do item que deseja remover: ") - 1;
        controller.removerItem(id, item);
        System.out.println("Item removido.");
        exibirPedido(controller.buscarPedido(id));
    }

    private void cancelarPedido() throws PedidoNaoEncontradoException {
        int id = lerInteiro("Número do pedido: ");
        controller.cancelarPedido(id);
        System.out.println("Pedido cancelado.");
    }

    private void simularProximaEtapa() throws PedidoNaoEncontradoException, StatusInvalidoException {
        int id = lerInteiro("Número do pedido: ");
        controller.avancarPedido(id);
        exibirPedido(controller.buscarPedido(id));
    }

    private void exibirPedido(Pedido pedido) {
        System.out.printf("%nPedido #%d | Cliente: %s | Status: %s%n",
                pedido.getId(), pedido.getCliente(), pedido.getStatus());
        int i = 1;
        for (Lanche lanche : pedido.getItens()) {
            System.out.printf("%d. %s - R$ %.2f%n", i++, lanche.getDescricao(), lanche.getPreco());
        }
        System.out.printf("Total: R$ %.2f%n", pedido.getTotal());
    }

    private String lerModelo() {
        System.out.println("Modelos disponíveis: tradicional / duplo");
        return lerTexto("Escolha o modelo: ");
    }

    private List<String> lerAdicionais() {
        System.out.println("Adicionais: queijo / bacon / batata");
        System.out.println("Digite os adicionais separados por vírgula ou pressione Enter para nenhum.");
        String entrada = lerTexto("Adicionais: ");
        List<String> adicionais = new ArrayList<>();
        if (!entrada.isBlank()) {
            for (String item : entrada.split(",")) adicionais.add(item.trim());
        }
        return adicionais;
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            String entrada = lerTexto(mensagem);
            try { return Integer.parseInt(entrada); }
            catch (NumberFormatException e) { System.out.println("Digite um número inteiro válido."); }
        }
    }
}
