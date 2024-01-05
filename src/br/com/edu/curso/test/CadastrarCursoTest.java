package br.com.edu.curso.test;

import br.com.edu.curso.dao.CursoDAO;
import br.com.edu.curso.dao.ICursoDAO;
import br.com.edu.curso.entity.Curso;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

public class CadastrarCursoTest {

//    @BeforeClass
//    static void connectDB(){
//        try{
//            Class.forName("org.mariadb.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection("jdbc:mariadb://localhost:3307/cursodb",
//                "root", "m0r20DBQ5)");
//    }

    @Test
    public void ct01_cadastraCurso_comSucesso() {
        LocalDateTime fim = LocalDateTime.of(2024, 02, 12, 22, 00);
        Curso curso = new Curso(5, "ads",
                "Análise e Desenvolvimento de Sistemas",
                true, LocalDateTime.now(), fim);
        ICursoDAO iCursoDAO = new CursoDAO();
        iCursoDAO.adicionar(curso);

        //como o atributo 'nome' é obrigatório no BD, quando ele não é nulo o curso existe
        assertNotNull(iCursoDAO.pesquisar(curso.getId()).getNome());
    }

    @Test
    public void ct02_cadastraCurso_semSucesso_nomeInvalido() {
        LocalDateTime fim = LocalDateTime.of(2024, 02, 12, 22, 00);
        Curso curso = new Curso(40, "",
                "Análise e Desenvolvimento de Sistemas",
                true, LocalDateTime.now(), fim);
        ICursoDAO iCursoDAO = new CursoDAO();
        iCursoDAO.adicionar(curso);
        System.out.println(iCursoDAO.pesquisar(curso.getId()).getNome());
        System.out.println(iCursoDAO.pesquisar(curso.getId()).getDescricao());

        assertNotNull(iCursoDAO.pesquisar(curso.getId()));
    }

}
