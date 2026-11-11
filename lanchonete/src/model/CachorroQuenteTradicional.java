package model;

public class CachorroQuenteTradicional extends CachorroQuente {
    public CachorroQuenteTradicional() {
        super("Cachorro-quente tradicional", 10.00);
    }
    @Override public Lanche clonar() { return new CachorroQuenteTradicional(); }
}
