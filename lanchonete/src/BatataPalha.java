public class BatataPalha extends AdicionalDecorator {
    public BatataPalha(Lanche lanche) { super(lanche); }
    @Override public String getDescricao() { return lanche.getDescricao() + " + batata palha"; }
    @Override public double getPreco() { return lanche.getPreco() + 2.00; }
    @Override public Lanche clonar() { return new BatataPalha(lanche.clonar()); } // clone profundo da cadeia
}
