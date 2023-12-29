package br.com.edu.curso.boundary;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CreditosBoundary extends Tela{
    @Override
    public Pane render() {
        BorderPane panePrincipal = new BorderPane();
        VBox panCreditos = new VBox();

        panePrincipal.setCenter(panCreditos);

        Button btnCurso = new Button("Cadastro Curso");
        btnCurso.setOnAction((e) -> {
            notificarComando("TELA-CURSO");
        });

        panCreditos.getChildren().addAll(
                new Label("Linkedin: https://www.linkedin.com/in/breno-marcondes/"),
                new Label("GitHub: https://github.com/BrenoZacharias"),
                btnCurso

        );
        return panePrincipal;
    }
}
