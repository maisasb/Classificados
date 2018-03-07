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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.luna.classificados.R;
import com.luna.classificados.helper.FirebaseAutenticacao;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Condominio;
import com.luna.classificados.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    public TextView linkNoAccount;
    public FirebaseAuth autenticacao;
    public EditText emailText;
    public EditText senhaText;
    public Button botaoLogin;
    public ValueEventListener valueEventListenerUsuario;
    public DatabaseReference referenciaBanco;
    public Preferencias preferencias;

    @Override
    protected void onStop() {
        super.onStop();
        if (referenciaBanco !=null)
            referenciaBanco.removeEventListener(valueEventListenerUsuario);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = FirebaseAutenticacao.getFirebaseAutenticacao();
        preferencias = new Preferencias(LoginActivity.this);

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

                                if (autenticacao.getCurrentUser() != null){
                                    inserirUsuarioArquivoPreferencias(autenticacao.getCurrentUser().getUid());
                                }

                                //Listener para verificar se o usuário já possui condomínio
                                valueEventListenerUsuario = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                        if (usuario != null){
                                            if (usuario.getCondominio() == null){
                                                abrirTelaCondominio();
                                            }else{
                                                inserirCondominioArquivoPreferencias(usuario.getCondominio());
                                                abrirTelaAnuncios();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                };
                                referenciaBanco = FirebaseBanco.getFirebaseBanco().child("usuarios").child(autenticacao.getCurrentUser().getUid());
                                referenciaBanco.addListenerForSingleValueEvent(valueEventListenerUsuario);

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
            Toast.makeText(LoginActivity.this, "Atualize o Google Play Services do seu celular.", Toast.LENGTH_SHORT).show();
            Log.e("ERRO AO LOGAR", e.getMessage());
        }



    }

    private void abrirTelaCadastro() {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity ( intent );

    }

    private void abrirTelaCondominio() {
        Intent intent = new Intent(LoginActivity.this, CondominioActivity.class);
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

            if(preferencias.getCondominio() != null && !preferencias.getCondominio().equals("")){
                abrirTelaAnuncios();
            }else{
                abrirTelaCondominio();
            }

        }
    }

    private void inserirUsuarioArquivoPreferencias(String uuid) {

        preferencias.salvarDadosUsuario( uuid );

    }

    private void inserirCondominioArquivoPreferencias(String id) {

        preferencias.salvarDadosCondominio( id );

    }



}
