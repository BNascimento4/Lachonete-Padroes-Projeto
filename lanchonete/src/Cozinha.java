public class Cozinha implements PedidoObserver {
    @Override public void atualizar(Pedido p) {
        System.out.println("  [Cozinha] Pedido #" + p.getId() + " agora está " + p.getStatus());
    }
}
