package br.com.edu.curso.dao;

import br.com.edu.curso.entity.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO implements ICursoDAO {
    private static final String URIDB = "jdbc:mariadb://localhost:3307/cursodb";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "m0r20DBQ5)";

    public CursoDAO() {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URIDB, USUARIO, PASSWORD);
    }

    @Override
    public void adicionar(Curso curso) {
        try{
            Connection con = getConnection();
            String sql = "INSERT INTO curso (id, nome, descricao, ativo, inicio, termino)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, curso.getId());
            stmt.setString(2, curso.getNome());
            stmt.setString(3, curso.getDescricao());
            stmt.setString(4, transformaBooleanEmString(curso.isAtivo()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(curso.getInicio()));
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(curso.getTermino()));
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Curso> pesquisarPorNome(String nome) {
        List<Curso> cursos = new ArrayList<>();
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM curso WHERE nome LIKE ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso c = new Curso();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setAtivo(transformaStringEmBoolean(rs.getString("ativo")));
                c.setInicio(rs.getTimestamp("inicio").toLocalDateTime());
                c.setTermino(rs.getTimestamp("termino").toLocalDateTime());
                cursos.add(c);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cursos;
    }

    @Override
    public void remover(long id) {
        try {
            Connection con = getConnection();
            String sql = "DELETE FROM curso WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void atualizar(long id, Curso c) {
        try {
            Connection con = getConnection();
            String sql = "UPDATE curso SET nome = ?, descricao = ?, ativo = ?, inicio = ?, termino = ? " +
                    "WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getDescricao());
            stmt.setString(3, transformaBooleanEmString(c.isAtivo()));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(c.getInicio()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(c.getTermino()));
            stmt.setLong(6, c.getId());
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Curso> pesquisarTodos() {
        List<Curso> cursos = new ArrayList<>();
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM curso";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getLong("id"));
                curso.setNome(rs.getString("nome"));
                curso.setDescricao(rs.getString("descricao"));
                curso.setAtivo(transformaStringEmBoolean(rs.getString("ativo")));
                curso.setInicio(rs.getTimestamp("inicio").toLocalDateTime());
                curso.setTermino(rs.getTimestamp("termino").toLocalDateTime());
                cursos.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public String transformaBooleanEmString(boolean booleano){
        if (booleano){
            return "t";
        }
        return "f";
    }

    public boolean transformaStringEmBoolean(String string) {
        if (string.equals("t")) {
            return true;
        }
        return false;
    }

}
