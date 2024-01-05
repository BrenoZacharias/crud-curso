package br.com.edu.curso.control;

import br.com.edu.curso.dao.CursoDAO;
import br.com.edu.curso.dao.ICursoDAO;
import br.com.edu.curso.entity.Curso;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CursoControl {

    public LongProperty id = new SimpleLongProperty(0);
    public StringProperty nome = new SimpleStringProperty("");
    public StringProperty descricao = new SimpleStringProperty("");
    public BooleanProperty ativo = new SimpleBooleanProperty(false);
    public ObjectProperty inicio = new SimpleObjectProperty(LocalDateTime.now());
    public ObjectProperty termino = new SimpleObjectProperty(LocalDateTime.now());

    private ICursoDAO iCursoDAO = new CursoDAO();

    private List<Curso> cursos = new ArrayList<>();
    private ObservableList<Curso> cursosObservable = FXCollections.observableArrayList();

    public CursoControl(){
        cursosObservable.addAll(iCursoDAO.pesquisarTodos());
    }

    public void adicionar() {
        boolean isCamposValidos = validarCampos();
        if (isCamposValidos) {
            cursosObservable.clear();
            Curso curso = toEntity();
            iCursoDAO.adicionar(curso);
            limparCampos();
            System.out.println(inicio);
            System.out.println(inicio.get());
            System.out.println(inicio.getValue());
            cursosObservable.addAll(iCursoDAO.pesquisarTodos());
        }

    }

    private boolean validarCampos() {
        String [] camposInvalidos = {"", "", ""};
        int cont = 0;
        if(nome.get() == ""){
            camposInvalidos[cont] = "nome";
            cont ++;
        }
        if(inicio.get() == null){
            camposInvalidos[cont] = "inicio";
            cont ++;
        }
        if(termino.get() == null) {
            camposInvalidos[cont] = "termino";
            cont++;
        }
        if(!camposInvalidos[0].equals("")){
            String textoAlerta = "O campo 'nome' deve conter um nome de curso \n" +
                    "Os campos de data devem ser passado como no exemplo a seguir: '03/01/2024 19:07' \n" +
                    "Campo(s) inválido(s):\n";
            for(int i = 0; i < cont;i ++) {
                textoAlerta = textoAlerta + camposInvalidos[i] + "\n";
            }
            new Alert(Alert.AlertType.ERROR, textoAlerta).showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public void limparCampos() {
        id.set(0);
        nome.set("");
        descricao.set("");
        ativo.set(false);
        inicio.set(LocalDateTime.now());
        termino.set(LocalDateTime.now());
    }

    public void pesquisar() {
        cursosObservable.clear();
        List<Curso> encontrados = iCursoDAO.pesquisarPorNome( nome.get() );
        cursosObservable.addAll(encontrados);
        if (!cursosObservable.isEmpty()){
            fromEntity(cursosObservable.get(0));
        }
    }

    public void salvar() {
        boolean isCamposValidos = validarCampos();
        if (isCamposValidos) {
            cursosObservable.clear();
            Curso curso = toEntity();
            if (curso.getId() == 0) {
                iCursoDAO.adicionar(curso);
            } else {
                iCursoDAO.atualizar(id.get(), curso);
            }
            limparCampos();
            cursosObservable.addAll(iCursoDAO.pesquisarTodos());
            new Alert(Alert.AlertType.INFORMATION, "Curso salvo com sucesso").showAndWait();
        }
    }

    private Curso toEntity() {
        Curso curso = new Curso();
        curso.setId(id.get());
        curso.setNome(nome.get());
        curso.setDescricao(descricao.get());
        curso.setAtivo(ativo.get());
        curso.setInicio((LocalDateTime)inicio.get());
        curso.setTermino((LocalDateTime)termino.get());
        return curso;
    }

    public void remover(long id) {
        cursosObservable.clear();
        iCursoDAO.remover(id);
        cursosObservable.addAll(iCursoDAO.pesquisarTodos());
    }

    public void fromEntity(Curso curso){
        try {
            id.set(curso.getId());
            nome.set(curso.getNome());
            descricao.set(curso.getDescricao());
            ativo.set(curso.isAtivo());
            inicio.set(curso.getInicio());
            termino.set(curso.getTermino());
        } catch (NullPointerException e){}

    }

    public ObservableList<Curso> getLista() { return cursosObservable;}

}
