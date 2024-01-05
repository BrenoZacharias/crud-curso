package br.com.edu.curso.test;

import br.com.edu.curso.dao.CursoDAO;
import br.com.edu.curso.dao.ICursoDAO;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ConsultarCursoTest {


    @Test
    //como o atributo 'nome' é obrigatório no BD, quando ele não é nulo o curso existe
    public void ct_consultarCurso_Existente() {
        ICursoDAO iCursoDAO = new CursoDAO();
        assertNotNull(iCursoDAO.pesquisar(1L).getNome());
    }

    @Test
    //como o atributo 'nome' é obrigatório no BD, quando ele é nulo o curso não existe
    public void ct_consultarCurso_Inexistente() {
        ICursoDAO iCursoDAO = new CursoDAO();
        assertNull(iCursoDAO.pesquisar(100L).getNome());
    }
}
