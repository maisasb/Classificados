package com.luna.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.luna.classificados.R;
import com.luna.classificados.helper.FirebaseAutenticacao;

public class LoginActivity extends AppCompatActivity {

    public TextView linkNoAccount;
    public FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        verificaUsuarioLogado();

        //TODO acao do botao - login

        linkNoAccount = findViewById(R.id.linkNoAccount);
        linkNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });


    }

    private void abrirTelaCadastro() {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity ( intent );
        finish();
    }

    private void abrirTelaAnuncios() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity ( intent );
        finish();
    }

    private void verificaUsuarioLogado() {
        autenticacao = FirebaseAutenticacao.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            abrirTelaAnuncios();
        }
    }

}
