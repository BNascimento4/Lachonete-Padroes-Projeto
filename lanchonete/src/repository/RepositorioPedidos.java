package repository;

import java.util.List;
import model.Pedido;
import model.exceptions.PedidoNaoEncontradoException;

public interface RepositorioPedidos {
    void salvar(Pedido pedido);
    Pedido buscarPorId(int id) throws PedidoNaoEncontradoException;
    List<Pedido> listar();
    int proximoId();
}
