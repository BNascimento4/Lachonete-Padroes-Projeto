package model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import model.exceptions.AdicionalInvalidoException;
import model.exceptions.ModeloInexistenteException;

/** Singleton + Prototype + registro das fábricas do Decorator. */
public final class Cardapio {
    private static Cardapio instancia;
    private final Map<String, Lanche> modelos = new LinkedHashMap<>();
    private final Map<String, Double> precosAdicionais = new LinkedHashMap<>();
    private final Map<String, UnaryOperator<Lanche>> adicionais = new LinkedHashMap<>();

    private Cardapio() {
        registrarModelo("tradicional", new CachorroQuenteTradicional());
        registrarModelo("duplo", new CachorroQuenteDuplo());
        registrarAdicional("queijo", 3.00, Queijo::new);
        registrarAdicional("bacon", 4.50, Bacon::new);
        registrarAdicional("batata", 2.00, BatataPalha::new);
    }

    public static synchronized Cardapio getInstance() {
        if (instancia == null) instancia = new Cardapio();
        return instancia;
    }

    private void registrarModelo(String nome, Lanche prototipo) {
        modelos.put(normalizar(nome), prototipo);
    }

    private void registrarAdicional(String nome, double preco, UnaryOperator<Lanche> fabrica) {
        adicionais.put(normalizar(nome), fabrica);
        precosAdicionais.put(normalizar(nome), preco);
    }

    public Lanche novoLanche(String modelo) throws ModeloInexistenteException {
        Lanche prototipo = modelos.get(normalizar(modelo));
        if (prototipo == null) throw new ModeloInexistenteException("Modelo '" + modelo + "' não existe.");
        return prototipo.clonar();
    }

    public Lanche adicionar(Lanche lanche, String adicional) throws AdicionalInvalidoException {
        UnaryOperator<Lanche> decorator = adicionais.get(normalizar(adicional));
        if (decorator == null) throw new AdicionalInvalidoException("Adicional '" + adicional + "' não existe.");
        return decorator.apply(lanche);
    }

    public Map<String, Double> getModelosDisponiveis() {
        Map<String, Double> resultado = new LinkedHashMap<>();
        modelos.forEach((nome, lanche) -> resultado.put(nome, lanche.getPreco()));
        return Collections.unmodifiableMap(resultado);
    }

    public Map<String, Double> getAdicionaisDisponiveis() {
        return Collections.unmodifiableMap(precosAdicionais);
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase();
    }
}
