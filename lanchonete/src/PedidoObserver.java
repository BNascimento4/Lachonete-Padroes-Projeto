/** Observer: quem quer ser avisado quando o pedido muda de status. */
public interface PedidoObserver {
    void atualizar(Pedido pedido);
}
