package com.luna.classificados.model;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Asus on 22/01/2018.
 */

public class Condominio {

    private String id;
    private String nome;

    public Condominio(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void salvaUsuario(DatabaseReference referenciaBanco, String idUsuarioLogado){
        DatabaseReference referenciaCondominio = referenciaBanco.child("condominios").child(getId());
        referenciaCondominio.child("usuarios").child(idUsuarioLogado).setValue(true);
    }

    @Override
    public String toString() {
        return getNome();
    }

}
