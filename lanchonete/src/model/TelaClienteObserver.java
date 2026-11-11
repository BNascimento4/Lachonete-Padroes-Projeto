package model;

public class TelaClienteObserver implements PedidoObserver {
    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[Cliente] Seu pedido #" + pedido.getId() + " agora está: " + pedido.getStatus());
    }
}
