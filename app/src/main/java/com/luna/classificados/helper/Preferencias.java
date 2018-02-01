package com.luna.classificados.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "classificados.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_CONDOMINIO = "condominioUsuarioLogado";

    public Preferencias( Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE );
        editor = preferences.edit();

    }

    public void salvarDadosUsuario( String identificadorUsuario){

        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.commit();

    }

    public void salvarDadosCondominio(String condominio){

        editor.putString(CHAVE_CONDOMINIO, condominio);
        editor.commit();

    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getCondominio(){
        return preferences.getString(CHAVE_CONDOMINIO, null);
    }

}
