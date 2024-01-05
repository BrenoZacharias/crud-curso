package br.com.edu.curso.service;

import br.com.edu.curso.dao.CursoDAO;
import br.com.edu.curso.dao.ICursoDAO;
import br.com.edu.curso.entity.Curso;

public class CursoService {

    private ICursoDAO iCursoDAO = new CursoDAO();

    public void adicionar (Curso curso) {
        iCursoDAO.adicionar(curso);
    }
}
