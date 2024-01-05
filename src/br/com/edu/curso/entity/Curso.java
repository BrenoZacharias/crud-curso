package br.com.edu.curso.entity;

import java.time.LocalDateTime;

public class Curso {
    private long id;
    private String nome;
    private String descricao;
    private boolean ativo;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public Curso() {
        super();
    }

    public Curso(long id, String nome, String descricao, boolean ativo, LocalDateTime inicio, LocalDateTime termino) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.inicio = inicio;
        this.termino = termino;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getTermino() {
        return termino;
    }

    public void setTermino(LocalDateTime termino) {
        this.termino = termino;
    }
}
