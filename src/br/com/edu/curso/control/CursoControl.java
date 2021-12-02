package br.com.edu.curso.control;

import br.com.edu.curso.dao.CursoDAO;
import br.com.edu.curso.dao.ICursoDAO;
import br.com.edu.curso.entity.Curso;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
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
        cursosObservable.clear();
        Curso curso = toEntity();
        iCursoDAO.adicionar(curso);
        limparCampos();
        cursosObservable.addAll(iCursoDAO.pesquisarTodos());
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
        cursosObservable.clear();
        Curso curso = toEntity();
        if (curso.getId() == 0) {
            iCursoDAO.adicionar( curso );
        } else{
            iCursoDAO.atualizar( id.get(), curso );
        }
        limparCampos();
        cursosObservable.addAll(iCursoDAO.pesquisarTodos());
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
        id.set(curso.getId());
        nome.set(curso.getNome());
        descricao.set(curso.getDescricao());
        ativo.set(curso.isAtivo());
        inicio.set(curso.getInicio());
        termino.set(curso.getTermino());
    }

    public ObservableList<Curso> getLista() { return cursosObservable;}

}
