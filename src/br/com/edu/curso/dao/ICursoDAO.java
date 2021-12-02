package br.com.edu.curso.dao;

import br.com.edu.curso.entity.Curso;

import java.util.List;

public interface ICursoDAO {
    void adicionar (Curso c);
    List<Curso> pesquisarPorNome(String nome);
    void remover(long id);
    void atualizar(long id, Curso c);
    List<Curso> pesquisarTodos();
}
