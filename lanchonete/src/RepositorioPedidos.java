import java.util.List;

/** Abstração da qual a Facade depende (DIP). */
public interface RepositorioPedidos {
    void salvar(Pedido pedido);
    Pedido buscarPorId(int id) throws PedidoNaoEncontradoException;
    List<Pedido> listar();
    int proximoId();
}
