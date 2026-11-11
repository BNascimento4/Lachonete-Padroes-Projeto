package controller;

import java.util.List;
import model.Pedido;
import service.LanchoneteFacade;
import model.exceptions.AdicionalInvalidoException;
import model.exceptions.ModeloInexistenteException;
import model.exceptions.PedidoNaoEncontradoException;
import model.exceptions.StatusInvalidoException;

/** Controller: recebe as ações da View e chama os casos de uso. */
public class LanchoneteController {
    private final LanchoneteFacade facade;

    public LanchoneteController(LanchoneteFacade facade) { this.facade = facade; }

    public Pedido criarPedido(String cliente, String modelo, List<String> adicionais)
            throws ModeloInexistenteException, AdicionalInvalidoException {
        return facade.fazerPedido(cliente, modelo, adicionais);
    }

    public void adicionarItem(int id, String modelo, List<String> adicionais)
            throws PedidoNaoEncontradoException, ModeloInexistenteException, AdicionalInvalidoException {
        facade.adicionarItem(id, modelo, adicionais);
    }

    public void removerItem(int id, int indice) throws PedidoNaoEncontradoException {
        facade.removerItem(id, indice);
    }

    public void cancelarPedido(int id) throws PedidoNaoEncontradoException {
        facade.cancelarPedido(id);
    }

    public void avancarPedido(int id) throws PedidoNaoEncontradoException, StatusInvalidoException {
        facade.avancarPedido(id);
    }

    public Pedido buscarPedido(int id) throws PedidoNaoEncontradoException { return facade.buscarPedido(id); }
    public List<Pedido> listarPedidos() { return facade.listarPedidos(); }
    public model.Cardapio getCardapio() { return facade.getCardapio(); }
}
