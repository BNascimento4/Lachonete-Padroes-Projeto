package model;

public class Cozinha implements PedidoObserver {
    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[Cozinha] Pedido #" + pedido.getId() + " atualizado: " + pedido.getStatus());
    }
}
