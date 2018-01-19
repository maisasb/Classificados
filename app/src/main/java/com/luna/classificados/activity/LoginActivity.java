package com.luna.classificados.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luna.classificados.R;

public class LoginActivity extends AppCompatActivity {

    public TextView linkNoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linkNoAccount = findViewById(R.id.linkNoAccount);
        linkNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity ( intent );

            }
        });


    }
}
