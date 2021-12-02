package br.com.edu.curso;

import br.com.edu.curso.boundary.CreditosBoundary;
import br.com.edu.curso.boundary.CursoBoundary;
import br.com.edu.curso.boundary.ExecutorComandos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application implements ExecutorComandos {
    private CursoBoundary cursoBoundary = new CursoBoundary();
    private CreditosBoundary creditosBoundary = new CreditosBoundary();
    private BorderPane bp = new BorderPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scn = new Scene(bp, 900, 600);

        MenuBar menuBar = new MenuBar();

        Menu mnuArquivo = new Menu("Arquivo");
        Menu mnuCadastros = new Menu("Cadastro");
        Menu mnuAjuda = new Menu("Ajuda");

        menuBar.getMenus().addAll(mnuArquivo, mnuCadastros, mnuAjuda);

        MenuItem mnuItemSair = new MenuItem("Sair");
        mnuItemSair.setOnAction((e) -> {
            executarComando("SAIR");
        });
        MenuItem mnuItemCursos = new MenuItem("Cursos");
        mnuItemCursos.setOnAction((e) -> {
            executarComando("TELA-CURSO");
        });
        MenuItem mnuItemCreditos = new MenuItem("Créditos");
        mnuItemCreditos.setOnAction((e) -> {
            executarComando("TELA-CREDITO");
        });

        cursoBoundary.adicionarExecutor(this);
        creditosBoundary.adicionarExecutor(this);

        mnuArquivo.getItems().addAll(mnuItemSair);
        mnuCadastros.getItems().addAll(mnuItemCursos);
        mnuAjuda.getItems().addAll(mnuItemCreditos);

        bp.setTop(menuBar);

        primaryStage.setScene( scn );
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }

    @Override
    public void executarComando(String comando) {
        if("TELA-CREDITO".equals(comando)) {
            bp.setCenter( creditosBoundary.render());
        } else if ("TELA-CURSO".equals(comando)) {
            bp.setCenter(cursoBoundary.render());
        } else if ("SAIR".equals(comando)) {
            Platform.exit();
            System.exit(0);
        }
    }
}
