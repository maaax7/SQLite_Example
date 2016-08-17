package com.example.rm73950.persistencia.bean;

import java.io.Serializable;

/**
 * Created by rm73950 on 10/08/2016.
 */
public class Produto implements Serializable {
    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return this.getNome() + "\n " + this.getDescricao() + "\n " + this.getPreco();
    }
}
