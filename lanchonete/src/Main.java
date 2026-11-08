public class Main {
    public static void main(String[] args) {
        // Composição das dependências (DI feita "na mão" no ponto de entrada)
        LanchoneteFacade lanchonete = new LanchoneteFacade(
                Cardapio.getInstance(),
                new RepositorioPedidosEmMemoria(),
                new Cozinha(), new TelaCliente());

        try {
            System.out.println("== Fazendo pedidos ==");
            Pedido p1 = lanchonete.fazerPedido("Ana", "duplo", "queijo", "bacon", "batata");
            lanchonete.fazerPedido("Bruno", "tradicional", "queijo");
            lanchonete.imprimirResumo(p1.getId());

            System.out.println("\n== Avançando status (Observer) ==");
            lanchonete.avancarPedido(1);
            lanchonete.avancarPedido(1);
            lanchonete.avancarPedido(1);

            System.out.println("\n== Provando o Singleton ==");
            System.out.println("Mesma instância? " + (Cardapio.getInstance() == Cardapio.getInstance()));

            System.out.println("\n== Provando o Prototype ==");
            Lanche a = Cardapio.getInstance().novoLanche("tradicional");
            Lanche b = Cardapio.getInstance().novoLanche("tradicional");
            System.out.println("Cópias independentes? " + (a != b));

            System.out.println("\n== Tratamento de erros ==");
            try { lanchonete.fazerPedido("Carla", "vegano"); }
            catch (ModeloInexistenteException e) { System.out.println("Erro: " + e.getMessage()); }
            try { lanchonete.fazerPedido("Carla", "duplo", "ketchup"); }
            catch (AdicionalInvalidoException e) { System.out.println("Erro: " + e.getMessage()); }
            try { lanchonete.avancarPedido(99); }
            catch (PedidoNaoEncontradoException e) { System.out.println("Erro: " + e.getMessage()); }
            try { lanchonete.avancarPedido(1); }
            catch (StatusInvalidoException e) { System.out.println("Erro: " + e.getMessage()); }

        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }
}
