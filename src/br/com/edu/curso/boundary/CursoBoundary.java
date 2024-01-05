package br.com.edu.curso.boundary;

import br.com.edu.curso.control.CursoControl;
import br.com.edu.curso.entity.Curso;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import javax.naming.Binding;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CursoBoundary extends Tela{
    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private TextField txtAtivo = new TextField();
    private TextField txtInicio = new TextField();
    private TextField txtTermino = new TextField();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnSalvar = new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnCreditos = new Button("Creditos");
    private Button btnLimparCampos = new Button("Limpar Campos");

    private CursoControl control = new CursoControl();

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm:ss");

    private TableView<Curso> table;

    private void criarTabela() {
        table = new TableView<>();

        TableColumn<Curso, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(
                new PropertyValueFactory<Curso, Long>("id")
        );

        TableColumn<Curso, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(
                new PropertyValueFactory<Curso, String>("nome")
        );

        TableColumn<Curso, String> col3 = new TableColumn<>("Descrição");
        col3.setCellValueFactory(
                new PropertyValueFactory<Curso, String>("descricao")
        );

        TableColumn<Curso, Boolean> col4 = new TableColumn<>("Ativo");
        col4.setCellValueFactory(
                new PropertyValueFactory<Curso, Boolean>("ativo")
        );

        TableColumn<Curso, String> col5 = new TableColumn<>("Início");
        col5.setCellValueFactory( (item) -> {
            LocalDateTime d = item.getValue().getInicio();
            return  new ReadOnlyStringWrapper(d.format(fmt));
        });

        TableColumn<Curso, String> col6 = new TableColumn<>("Termino");
        col6.setCellValueFactory( (item) -> {
            LocalDateTime d = item.getValue().getTermino();
            return new ReadOnlyStringWrapper(d.format(fmt));
        });

        TableColumn<Curso, String> col7 = new TableColumn<>("Ações");
        col7.setCellFactory( (tbcol) -> {
            Button btnRemover = new Button("Remover");
            TableCell<Curso, String> tcell = new TableCell<Curso, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnRemover.setOnAction( (e) -> {
                            Curso c = getTableView().getItems().get(getIndex());
                            control.remover(c.getId());
                        });
                        setGraphic(btnRemover);
                        setText(null);
                    }
                }
            };
            return tcell;
        }
        );

        table.getSelectionModel().selectedItemProperty().addListener( (obs, old, novo) -> {
            control.fromEntity(novo);
        } );

        table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        table.setItems(control.getLista());
    }

    @Override
    public Pane render() {
        BorderPane panPrincipal = new BorderPane();
        GridPane panCampos = new GridPane();

        criarTabela();

        panPrincipal.setTop(panCampos);
        panPrincipal.setCenter(table);

        txtId.setEditable(false);

        Bindings.bindBidirectional(txtId.textProperty(), control.id, new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nome);
        Bindings.bindBidirectional(txtDescricao.textProperty(), control.descricao);
        Bindings.bindBidirectional(txtAtivo.textProperty(), control.ativo, new BooleanStringConverter());
        Bindings.bindBidirectional(txtInicio.textProperty(), control.inicio,
                new LocalDateTimeStringConverter());
        Bindings.bindBidirectional(txtTermino.textProperty(), control.termino,
                new LocalDateTimeStringConverter());

        panCampos.add(new Label("Id:"), 0, 0);
        panCampos.add(txtId, 1, 0);
        panCampos.add(new Label("Nome:"),0, 1);
        panCampos.add(txtNome, 1, 1);
        panCampos.add(new Label("Descrição:"), 0, 2);
        panCampos.add(txtDescricao, 1,2);
        panCampos.add(new Label("Ativo:"), 0, 3);
        panCampos.add(txtAtivo, 1,3);
        panCampos.add(new Label("Início:"), 0,4);
        panCampos.add(txtInicio, 1, 4);
        panCampos.add(new Label("Término:"),0,5);
        panCampos.add(txtTermino, 1,5);

        panCampos.add(btnAdicionar, 0,6);
        panCampos.add(btnSalvar, 1, 6);
        panCampos.add(btnPesquisar, 2,6);
        panCampos.add(btnLimparCampos, 3, 6);
        panCampos.add(btnCreditos,4,6);

        btnAdicionar.setOnAction( (e) -> {
            control.adicionar();
        });

        btnPesquisar.setOnAction( (e) -> {
            control.pesquisar();
        });

        btnSalvar.setOnAction( (e) -> {
            control.salvar();
        });

        btnLimparCampos.setOnAction( (e) -> {
            control.limparCampos();
        });

        btnCreditos.setOnAction( (e) -> {
            notificarComando("TELA-CREDITO");
        });
        return panPrincipal;
    }
}
