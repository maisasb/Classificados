package com.luna.classificados.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.luna.classificados.helper.FirebaseBanco;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String condominio;

    public Usuario(){

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome () { return nome; }

    public void setNome (String nome ) {this.nome = nome;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCondominio() {
        return condominio;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvar(){

        DatabaseReference reference = FirebaseBanco.getFirebaseBanco();
        reference.child("usuarios").child(getId()).setValue(this);

    }

    public void salvaCondominio(DatabaseReference referenciaBanco){

        DatabaseReference referenciaUsuario = referenciaBanco.child("usuarios").child(getId());
        referenciaUsuario.child("condominio").setValue(getCondominio());

    }


}
