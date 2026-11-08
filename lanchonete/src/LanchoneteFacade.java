/**
 * FACADE: interface simples para um subsistema com várias classes
 * (Cardapio, decorators, Pedido, observers, repositório).
 * DI: as dependências chegam pelo construtor (e são abstrações quando possível).
 */
public class LanchoneteFacade {
    private final Cardapio cardapio;
    private final RepositorioPedidos repositorio;
    private final PedidoObserver[] observadoresPadrao;

    public LanchoneteFacade(Cardapio cardapio, RepositorioPedidos repositorio, PedidoObserver... observadoresPadrao) {
        this.cardapio = cardapio;
        this.repositorio = repositorio;
        this.observadoresPadrao = observadoresPadrao;
    }

    public Pedido fazerPedido(String cliente, String modelo, String... adicionais)
            throws ModeloInexistenteException, AdicionalInvalidoException {
        Lanche lanche = cardapio.novoLanche(modelo);
        for (String a : adicionais) lanche = cardapio.adicionar(lanche, a);

        Pedido pedido = new Pedido(repositorio.proximoId(), cliente);
        pedido.adicionarItem(lanche);
        for (PedidoObserver o : observadoresPadrao) pedido.adicionarObservador(o);
        repositorio.salvar(pedido);
        return pedido;
    }

    public void avancarPedido(int id) throws PedidoNaoEncontradoException, StatusInvalidoException {
        repositorio.buscarPorId(id).avancarStatus();
    }

    public void imprimirResumo(int id) throws PedidoNaoEncontradoException {
        Pedido p = repositorio.buscarPorId(id);
        System.out.println("Pedido #" + p.getId() + " (" + p.getCliente() + ") - " + p.getStatus());
        for (Lanche l : p.getItens())
            System.out.printf("  - %s: R$ %.2f%n", l.getDescricao(), l.getPreco());
        System.out.printf("  Total: R$ %.2f%n", p.getTotal());
    }
}
