import java.util.ArrayList;
import java.util.List;

/** Subject do Observer. Associação com Lanche (coleção) e com observadores (coleção). */
public class Pedido {
    private final int id;
    private final String cliente;
    private final List<Lanche> itens = new ArrayList<>();
    private final List<PedidoObserver> observadores = new ArrayList<>();
    private StatusPedido status = StatusPedido.RECEBIDO;

    public Pedido(int id, String cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public void adicionarItem(Lanche l) { itens.add(l); }
    public void adicionarObservador(PedidoObserver o) { observadores.add(o); }
    public void removerObservador(PedidoObserver o) { observadores.remove(o); }

    public void avancarStatus() throws StatusInvalidoException {
        status = status.proximo();
        for (PedidoObserver o : observadores) o.atualizar(this);
    }

    public double getTotal() {
        double total = 0;
        for (Lanche l : itens) total += l.getPreco();
        return total;
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public StatusPedido getStatus() { return status; }
    public List<Lanche> getItens() { return List.copyOf(itens); }
}
