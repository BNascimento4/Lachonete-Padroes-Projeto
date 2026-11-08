import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * SINGLETON: um único cardápio na aplicação.
 * Guarda os PROTÓTIPOS (modelos prontos) e os adicionais (decorators) disponíveis.
 */
public class Cardapio {
    private static Cardapio instancia;

    private final Map<String, Lanche> modelos = new HashMap<>();
    private final Map<String, UnaryOperator<Lanche>> adicionais = new HashMap<>();

    private Cardapio() { // construtor privado
        registrarModelo("tradicional", new CachorroQuenteTradicional());
        registrarModelo("duplo", new CachorroQuenteDuplo());
        registrarAdicional("queijo", Queijo::new);
        registrarAdicional("bacon", Bacon::new);
        registrarAdicional("batata", BatataPalha::new);
    }

    public static synchronized Cardapio getInstance() {
        if (instancia == null) instancia = new Cardapio();
        return instancia;
    }

    public void registrarModelo(String nome, Lanche prototipo) { modelos.put(nome.toLowerCase(), prototipo); }
    public void registrarAdicional(String nome, UnaryOperator<Lanche> fabrica) { adicionais.put(nome.toLowerCase(), fabrica); }

    /** Prototype: devolve uma CÓPIA do modelo, nunca o original. */
    public Lanche novoLanche(String modelo) throws ModeloInexistenteException {
        Lanche proto = modelos.get(modelo.toLowerCase());
        if (proto == null) throw new ModeloInexistenteException("Modelo '" + modelo + "' não existe no cardápio");
        return proto.clonar();
    }

    /** Decorator: embrulha o lanche com o adicional pedido. */
    public Lanche adicionar(Lanche lanche, String adicional) throws AdicionalInvalidoException {
        UnaryOperator<Lanche> deco = adicionais.get(adicional.toLowerCase());
        if (deco == null) throw new AdicionalInvalidoException("Adicional '" + adicional + "' não existe");
        return deco.apply(lanche);
    }
}
