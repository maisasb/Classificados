package com.luna.classificados.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.luna.classificados.helper.FirebaseBanco;

public class Usuario {

    private String id;
    private String email;
    private String senha;
    private String idCondominio;

    public Usuario(){

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCondominio() {
        return idCondominio;
    }

    public void setIdCondominio(String idCondominio) {
        this.idCondominio = idCondominio;
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

}
