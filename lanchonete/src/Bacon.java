public class Bacon extends AdicionalDecorator {
    public Bacon(Lanche lanche) { super(lanche); }
    @Override public String getDescricao() { return lanche.getDescricao() + " + bacon"; }
    @Override public double getPreco() { return lanche.getPreco() + 4.50; }
    @Override public Lanche clonar() { return new Bacon(lanche.clonar()); } // clone profundo da cadeia
}
