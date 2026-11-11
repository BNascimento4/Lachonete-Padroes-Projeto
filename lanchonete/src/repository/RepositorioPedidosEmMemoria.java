package repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Pedido;
import model.exceptions.PedidoNaoEncontradoException;

/** Implementação mockada: os dados existem somente durante a execução. */
public class RepositorioPedidosEmMemoria implements RepositorioPedidos {
    private final Map<Integer, Pedido> pedidos = new LinkedHashMap<>();
    private int proximoId = 1;

    @Override public void salvar(Pedido pedido) { pedidos.put(pedido.getId(), pedido); }

    @Override
    public Pedido buscarPorId(int id) throws PedidoNaoEncontradoException {
        Pedido pedido = pedidos.get(id);
        if (pedido == null) throw new PedidoNaoEncontradoException("Pedido #" + id + " não encontrado.");
        return pedido;
    }

    @Override public List<Pedido> listar() { return new ArrayList<>(pedidos.values()); }
    @Override public int proximoId() { return proximoId++; }
}
