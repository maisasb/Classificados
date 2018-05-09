package com.luna.classificados.model;

/**
 * Created by natalia.vaz on 06/03/2018.
 */

import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.luna.classificados.R;
import com.luna.classificados.activity.LoginActivity;
import com.luna.classificados.helper.FirebaseBanco;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Negocio implements Serializable{

    private String id;
    private String usuario;
    private String nome;
    private String descBreve;
    private String descricao;
    private String categoria;
    private String contato;
    private boolean status;
    private boolean whatsapp;
    public String condominio;

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(boolean whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getCondominio() {
        return condominio;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }

    public void salvar(){

        DatabaseReference reference = FirebaseBanco.getFirebaseBanco();
        reference.child("negocios").child(getId()).setValue(this);

    }

    public void atualizar(){
        DatabaseReference reference = FirebaseBanco.getFirebaseBanco().child("negocios").child(getId());
        reference.updateChildren(toMap());
    }

    public boolean remover(){
        DatabaseReference reference = FirebaseBanco.getFirebaseBanco().child("negocios").child(getId());
        Task task =  reference.removeValue();
        return task.isSuccessful();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("nome", nome);
        result.put("descBreve", descBreve);
        result.put("descricao", descricao);
        result.put("usuario", usuario);
        result.put("categoria", categoria);
        result.put("contato", contato);
        result.put("status", status);
        result.put("whatsapp", whatsapp);
        result.put("condominio", condominio);

        return result;
    }

    @Override
    public String toString() {
        return getId() + " - " +getNome() ;
    }
}

