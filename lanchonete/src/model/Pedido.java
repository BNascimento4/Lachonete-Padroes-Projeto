package model;

import java.util.ArrayList;
import java.util.List;
import model.exceptions.StatusInvalidoException;

/** Entidade e Subject do Observer. */
public class Pedido {
    private final int id;
    private final String cliente;
    private final List<Lanche> itens = new ArrayList<>();
    private final List<PedidoObserver> observadores = new ArrayList<>();
    private StatusPedido status = StatusPedido.RECEBIDO;

    public Pedido(int id, String cliente) {
        if (cliente == null || cliente.isBlank()) throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        this.id = id;
        this.cliente = cliente.trim();
    }

    public void adicionarItem(Lanche lanche) {
        validarEdicao();
        if (lanche == null) throw new IllegalArgumentException("O lanche não pode ser nulo.");
        itens.add(lanche);
    }

    public void removerItem(int indice) {
        validarEdicao();
        if (indice < 0 || indice >= itens.size()) throw new IndexOutOfBoundsException("Item não encontrado.");
        if (itens.size() == 1) throw new IllegalStateException("Um pedido precisa ter ao menos um item.");
        itens.remove(indice);
    }

    public void adicionarObservador(PedidoObserver observador) {
        if (observador != null && !observadores.contains(observador)) observadores.add(observador);
    }

    public void removerObservador(PedidoObserver observador) { observadores.remove(observador); }

    public void avancarStatus() throws StatusInvalidoException {
        status = status.proximo();
        notificarObservadores();
    }

    public void cancelar() {
        if (!status.permiteEdicao()) {
            throw new IllegalStateException("O pedido só pode ser cancelado enquanto estiver como RECEBIDO.");
        }
        status = StatusPedido.CANCELADO;
        notificarObservadores();
    }

    private void validarEdicao() {
        if (!status.permiteEdicao()) {
            throw new IllegalStateException("Só é possível alterar pedidos com status RECEBIDO.");
        }
    }

    private void notificarObservadores() {
        for (PedidoObserver observador : List.copyOf(observadores)) observador.atualizar(this);
    }

    public double getTotal() {
        return itens.stream().mapToDouble(Lanche::getPreco).sum();
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public StatusPedido getStatus() { return status; }
    public List<Lanche> getItens() { return List.copyOf(itens); }
}
