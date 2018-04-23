package com.luna.classificados.model;

/**
 * Created by natalia.vaz on 08/03/2018.
 */

public class Categoria {
    private String id;
    private String tipo;

    public Categoria() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return getTipo();
    }
}


