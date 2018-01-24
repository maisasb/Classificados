package com.luna.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.luna.classificados.R;
import com.luna.classificados.adapter.CondominioAdapter;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Condominio;

import java.util.ArrayList;

public class CondominioActivity extends AppCompatActivity {

    public Spinner condominiumSpinner;
    public DatabaseReference referenciaBanco;
    public ArrayList<Condominio> condominioLista;
    public CondominioAdapter dataAdapter;
    public Button botaoContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_condominum);

        condominioLista = new ArrayList<>();

        condominiumSpinner = findViewById(R.id.condominiumSpinner);
        botaoContinuar =  findViewById(R.id.botaoContinuar);

        referenciaBanco = FirebaseBanco.getFirebaseBanco();
        referenciaBanco.child("condominios");

        referenciaBanco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot condominiosId : dataSnapshot.getChildren()){

                    for (DataSnapshot dados : condominiosId.getChildren()){

                        Condominio condominio = dados.getValue(Condominio.class);
                        condominio.setId(dados.getKey());
                        condominioLista.add(condominio);
                        dataAdapter.notifyDataSetChanged();

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Condominio condominioSelecionado = (Condominio) condominiumSpinner.getSelectedItem();

                Preferencias preferencias = new Preferencias(CondominioActivity.this);
                String idUsuarioLogado = preferencias.getIdentificador();

                referenciaBanco = FirebaseBanco.getFirebaseBanco();

                //Salva usuário no condomínio
                DatabaseReference referenciaCondominio = referenciaBanco.child("condominios").child(condominioSelecionado.getId());
                referenciaCondominio.child("usuarios").child(idUsuarioLogado).setValue(true);

                //Salva condominio no usuário
                DatabaseReference referenciaUsuario = referenciaBanco.child("usuarios").child(idUsuarioLogado);
                referenciaUsuario.child("condominio").setValue(condominioSelecionado.getId());

                Intent intent = new Intent(CondominioActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        dataAdapter = new CondominioAdapter(this, condominioLista);
        condominiumSpinner.setAdapter(dataAdapter);

    }

}
