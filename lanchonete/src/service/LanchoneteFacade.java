package service;

import java.util.List;
import model.Cardapio;
import model.Lanche;
import model.Pedido;
import model.PedidoObserver;
import repository.RepositorioPedidos;
import model.exceptions.AdicionalInvalidoException;
import model.exceptions.ModeloInexistenteException;
import model.exceptions.PedidoNaoEncontradoException;
import model.exceptions.StatusInvalidoException;

/** Facade: concentra os casos de uso e mantém a View longe das regras de negócio. */
public class LanchoneteFacade {
    private final Cardapio cardapio;
    private final RepositorioPedidos repositorio;
    private final List<PedidoObserver> observadores;

    public LanchoneteFacade(Cardapio cardapio, RepositorioPedidos repositorio,
                            List<PedidoObserver> observadores) {
        this.cardapio = cardapio;
        this.repositorio = repositorio;
        this.observadores = List.copyOf(observadores);
    }

    public Pedido fazerPedido(String cliente, String modelo, List<String> adicionais)
            throws ModeloInexistenteException, AdicionalInvalidoException {
        Lanche lanche = cardapio.novoLanche(modelo);
        for (String adicional : adicionais) lanche = cardapio.adicionar(lanche, adicional);

        Pedido pedido = new Pedido(repositorio.proximoId(), cliente);
        pedido.adicionarItem(lanche);
        observadores.forEach(pedido::adicionarObservador);
        repositorio.salvar(pedido);
        return pedido;
    }

    public void adicionarItem(int id, String modelo, List<String> adicionais)
            throws PedidoNaoEncontradoException, ModeloInexistenteException, AdicionalInvalidoException {
        Pedido pedido = repositorio.buscarPorId(id);
        Lanche lanche = cardapio.novoLanche(modelo);
        for (String adicional : adicionais) lanche = cardapio.adicionar(lanche, adicional);
        pedido.adicionarItem(lanche);
    }

    public void removerItem(int id, int indice) throws PedidoNaoEncontradoException {
        repositorio.buscarPorId(id).removerItem(indice);
    }

    public void cancelarPedido(int id) throws PedidoNaoEncontradoException {
        repositorio.buscarPorId(id).cancelar();
    }

    public void avancarPedido(int id) throws PedidoNaoEncontradoException, StatusInvalidoException {
        repositorio.buscarPorId(id).avancarStatus();
    }

    public Pedido buscarPedido(int id) throws PedidoNaoEncontradoException {
        return repositorio.buscarPorId(id);
    }

    public List<Pedido> listarPedidos() { return repositorio.listar(); }
    public Cardapio getCardapio() { return cardapio; }
}
