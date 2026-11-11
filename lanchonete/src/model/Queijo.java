package model;

public class Queijo extends AdicionalDecorator {
    public Queijo(Lanche lanche) { super(lanche); }
    @Override public String getDescricao() { return lanche.getDescricao() + " + queijo"; }
    @Override public double getPreco() { return lanche.getPreco() + 3.00; }
    @Override public Lanche clonar() { return new Queijo(lanche.clonar()); }
}
