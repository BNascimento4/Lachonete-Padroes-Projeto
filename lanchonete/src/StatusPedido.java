public enum StatusPedido {
    RECEBIDO, EM_PREPARO, PRONTO, ENTREGUE;

    public StatusPedido proximo() throws StatusInvalidoException {
        if (this == ENTREGUE) throw new StatusInvalidoException("Pedido já foi entregue");
        return values()[ordinal() + 1];
    }
}
