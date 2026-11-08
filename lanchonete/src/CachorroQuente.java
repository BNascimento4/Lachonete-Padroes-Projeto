/** Componente concreto (abstrato) do Decorator. Subclasses especializam preço e descrição (herança + polimorfismo). */
public abstract class CachorroQuente implements Lanche {
    protected final String descricao;
    protected final double preco;

    protected CachorroQuente(String descricao, double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }

    @Override public String getDescricao() { return descricao; }
    @Override public double getPreco() { return preco; }
}
