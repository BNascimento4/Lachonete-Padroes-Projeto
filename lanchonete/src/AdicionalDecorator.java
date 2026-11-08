/** Decorator abstrato: envolve um Lanche e delega, acrescentando comportamento. */
public abstract class AdicionalDecorator implements Lanche {
    protected final Lanche lanche;

    protected AdicionalDecorator(Lanche lanche) {
        if (lanche == null) throw new IllegalArgumentException("Lanche base não pode ser nulo");
        this.lanche = lanche;
    }

    @Override public String getDescricao() { return lanche.getDescricao(); }
    @Override public double getPreco() { return lanche.getPreco(); }
}
