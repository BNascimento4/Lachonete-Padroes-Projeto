package model;

public class CachorroQuenteDuplo extends CachorroQuente {
    public CachorroQuenteDuplo() {
        super("Cachorro-quente duplo (2 salsichas)", 15.00);
    }
    @Override public Lanche clonar() { return new CachorroQuenteDuplo(); }
}
