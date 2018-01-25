package com.luna.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.luna.classificados.R;
import com.luna.classificados.helper.FirebaseAutenticacao;
import com.luna.classificados.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    public TextView linkNoAccount;
    public FirebaseAuth autenticacao;
    public EditText emailText;
    public EditText senhaText;
    public Button botaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = FirebaseAutenticacao.getFirebaseAutenticacao();
        verificaUsuarioLogado();

        emailText = findViewById(R.id.emailText);
        senhaText = findViewById(R.id.senhaText);
        botaoLogin = findViewById(R.id.buttonLogin);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaFormulario()){

                    Usuario usuario = new Usuario();
                    usuario.setEmail(emailText.getText().toString());
                    usuario.setSenha(senhaText.getText().toString());
                    validarLogin(usuario);

                }else{
                    Toast.makeText(LoginActivity.this, R.string.login_complete_fields, Toast.LENGTH_SHORT).show();
                }



            }
        });

        linkNoAccount = findViewById(R.id.linkNoAccount);
        linkNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });


    }

    private boolean validaFormulario() {

        if (emailText.getText().length() == 0){
            return false;
        }
        if (senhaText.getText().length() == 0){
            return false;
        }

        return true;
    }

    private void validarLogin(Usuario usuario) {

        try{

            autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                abrirTelaAnuncios();

                            }else{

                                try{

                                    throw task.getException();

                                }catch (FirebaseAuthInvalidUserException e){
                                    Toast.makeText(LoginActivity.this, R.string.login_error_email, Toast.LENGTH_SHORT).show();
                                }catch(FirebaseAuthInvalidCredentialsException e){
                                    Toast.makeText(LoginActivity.this, R.string.login_error_pass, Toast.LENGTH_SHORT).show();
                                }catch(Exception e){
                                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                                }


                            }

                        }
                    });

        }catch(Exception e){
            Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
            Log.e("ERRO AO LOGAR", e.getMessage());
        }



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
        if (autenticacao.getCurrentUser() != null) {
            abrirTelaAnuncios();
        }
    }


}
