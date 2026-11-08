/** Componente comum: tanto o cachorro-quente base quanto os decorators são um Lanche (Decorator + Prototype). */
public interface Lanche {
    String getDescricao();
    double getPreco();
    Lanche clonar(); // Prototype
}
