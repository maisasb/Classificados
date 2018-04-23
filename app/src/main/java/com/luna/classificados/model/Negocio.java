package com.luna.classificados.model;

/**
 * Created by natalia.vaz on 06/03/2018.
 */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.luna.classificados.helper.FirebaseBanco;

public class Negocio {

    private String id;
    private String usuario;
    private String nome;
    private String descBreve;
    private String descricao;
    private String categoria;
    private String contato;

    public Negocio(){

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescBreve() {
        return descBreve;
    }

    public void setDescBreve(String descBreve) {
        this.descBreve = descBreve;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }


    public void salvar(){

        DatabaseReference reference = FirebaseBanco.getFirebaseBanco();
        reference.child("negocios").child(getId()).setValue(this);

    }

    @Override
    public String toString() {
        return getId() + " - " +getNome() ;
    }
}

