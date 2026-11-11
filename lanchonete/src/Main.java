import java.util.List;
import controller.LanchoneteController;
import model.Cardapio;
import model.Cozinha;
import model.Pedido;
import model.PedidoObserver;
import model.TelaClienteObserver;
import repository.RepositorioPedidos;
import repository.RepositorioPedidosEmMemoria;
import service.LanchoneteFacade;
import view.ConsoleView;

/** Composition Root: instancia e conecta as dependências da aplicação. */
public class Main {
    public static void main(String[] args) {
        RepositorioPedidos repositorio = new RepositorioPedidosEmMemoria();
        List<PedidoObserver> observadores = List.of(new Cozinha(), new TelaClienteObserver());
        LanchoneteFacade facade = new LanchoneteFacade(Cardapio.getInstance(), repositorio, observadores);
        LanchoneteController controller = new LanchoneteController(facade);

        carregarDadosMockados(facade);
        new ConsoleView(controller).iniciar();
    }

    private static void carregarDadosMockados(LanchoneteFacade facade) {
        try {
            Pedido ana = facade.fazerPedido("Ana", "duplo", List.of("queijo", "bacon", "batata"));
            facade.fazerPedido("Bruno", "tradicional", List.of("queijo"));
            System.out.println("Dados de demonstração carregados. Pedido inicial da Ana: #" + ana.getId());
        } catch (Exception e) {
            System.out.println("Não foi possível carregar os dados de demonstração: " + e.getMessage());
        }
    }
}
