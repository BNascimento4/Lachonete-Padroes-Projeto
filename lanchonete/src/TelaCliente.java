public class TelaCliente implements PedidoObserver {
    @Override public void atualizar(Pedido p) {
        System.out.println("  [Cliente " + p.getCliente() + "] Seu pedido #" + p.getId() + ": " + p.getStatus());
    }
}
