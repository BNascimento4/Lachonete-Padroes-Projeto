package model;

import model.exceptions.StatusInvalidoException;

public enum StatusPedido {
    RECEBIDO, EM_PREPARACAO, PRONTO, ENTREGUE, CANCELADO;

    public StatusPedido proximo() throws StatusInvalidoException {
        return switch (this) {
            case RECEBIDO -> EM_PREPARACAO;
            case EM_PREPARACAO -> PRONTO;
            case PRONTO -> ENTREGUE;
            default -> throw new StatusInvalidoException("Não é possível avançar um pedido com status " + this + ".");
        };
    }

    public boolean permiteEdicao() {
        return this == RECEBIDO;
    }
}
