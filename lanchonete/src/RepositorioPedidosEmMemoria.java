import java.util.ArrayList;
import java.util.List;

public class RepositorioPedidosEmMemoria implements RepositorioPedidos {
    private final List<Pedido> pedidos = new ArrayList<>();
    private int contador = 0;

    @Override public void salvar(Pedido p) { pedidos.add(p); }
    @Override public int proximoId() { return ++contador; }
    @Override public List<Pedido> listar() { return List.copyOf(pedidos); }

    @Override public Pedido buscarPorId(int id) throws PedidoNaoEncontradoException {
        for (Pedido p : pedidos) if (p.getId() == id) return p;
        throw new PedidoNaoEncontradoException("Pedido #" + id + " não encontrado");
    }
}
