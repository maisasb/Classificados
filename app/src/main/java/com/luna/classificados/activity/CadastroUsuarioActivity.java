package com.luna.classificados.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.luna.classificados.R;
import com.luna.classificados.helper.FirebaseAutenticacao;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Usuario;


public class CadastroUsuarioActivity extends AppCompatActivity{

    public EditText emailText;
    public EditText senhaText;
    public Button buttonCadastrar;
    public EditText confirmarSenhaText;
    public TextView textErrorConfirmPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        emailText = findViewById(R.id.emailText);
        senhaText = findViewById(R.id.senhaText);
        confirmarSenhaText = findViewById(R.id.confirmarSenhaText);
        textErrorConfirmPass = findViewById(R.id.textErrorConfirmPass);


        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verifyPassword()){

                    Usuario usuario = new Usuario();
                    usuario.setEmail(emailText.getText().toString());
                    usuario.setSenha(senhaText.getText().toString());

                    cadastrarUsuario(usuario);

                }


            }
        });

        confirmarSenhaText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b){

                    verifyPassword();

                }

            }
        });



    }

    private boolean verifyPassword() {

        if (!senhaText.getText().toString().equals(confirmarSenhaText.getText().toString())){

            textErrorConfirmPass.setText(R.string.add_error_confirmPass);
            confirmarSenhaText.getBackground().setColorFilter(getResources().getColor(R.color.error),
                    PorterDuff.Mode.SRC_ATOP);

            return false;

        }else{
            textErrorConfirmPass.setText("");
            confirmarSenhaText.getBackground().setColorFilter(getResources().getColor(R.color.textPrimary),
                    PorterDuff.Mode.SRC_ATOP);
        }

        return true;

    }

    private void cadastrarUsuario(Usuario usuario){

        final FirebaseAuth firebaseAutenticacao = FirebaseAutenticacao.getFirebaseAutenticacao();
        firebaseAutenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(CadastroUsuarioActivity.this, R.string.add_success,  Toast.LENGTH_SHORT).show();

                            FirebaseUser user = task.getResult().getUser();

                            salvaUsuario(user);

                            inserirUsuarioArquivoPreferencias(user.getUid());

                            selecionaCondominioActivity();

                        }else{

                            try {
                                if (task.getException() != null){
                                    throw task.getException();
                                }
                            }catch(FirebaseAuthWeakPasswordException e){
                                Toast.makeText(CadastroUsuarioActivity.this, R.string.add_weakpass,  Toast.LENGTH_SHORT).show();
                            }catch(FirebaseAuthInvalidCredentialsException e){
                                Toast.makeText(CadastroUsuarioActivity.this, R.string.add_error_invalidmail,  Toast.LENGTH_SHORT).show();
                            }catch(FirebaseAuthUserCollisionException e){
                                Toast.makeText(CadastroUsuarioActivity.this, R.string.add_error_mailalready,  Toast.LENGTH_SHORT).show();
                            }catch(Exception e){
                                Toast.makeText(CadastroUsuarioActivity.this, R.string.add_error,  Toast.LENGTH_SHORT).show();
                            }


                        }

                    }
                });

    }

    private void inserirUsuarioArquivoPreferencias(String uuid) {

        Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
        preferencias.salvarDados( uuid );

    }

    private void selecionaCondominioActivity() {

        Intent intent = new Intent(CadastroUsuarioActivity.this, CondominioActivity.class);
        startActivity ( intent );

        finish();

    }

    private void salvaUsuario(FirebaseUser user) {

        Usuario usuario = new Usuario();
        usuario.setId(user.getUid());
        usuario.setEmail(user.getEmail());

        usuario.salvar();



    }

}
