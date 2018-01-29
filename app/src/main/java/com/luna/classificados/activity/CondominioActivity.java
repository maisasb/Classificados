package com.luna.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.luna.classificados.R;
import com.luna.classificados.adapter.CondominioAdapter;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Condominio;
import com.luna.classificados.model.Usuario;

import java.util.ArrayList;

public class CondominioActivity extends AppCompatActivity {

    public Spinner condominiumSpinner;
    public DatabaseReference referenciaBanco;
    public ArrayList<Condominio> condominioLista;
    public ArrayAdapter dataAdapter;
    public Button botaoContinuar;
    public ValueEventListener valueEventListenerCondominio;

    @Override
    protected void onStop() {
        super.onStop();
        referenciaBanco.removeEventListener(valueEventListenerCondominio);
    }

    @Override
    protected void onStart() {
        super.onStart();
        referenciaBanco.addListenerForSingleValueEvent(valueEventListenerCondominio);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_condominum);

        condominioLista = new ArrayList<>();

        condominiumSpinner = findViewById(R.id.condominiumSpinner);
        botaoContinuar =  findViewById(R.id.botaoContinuar);

        referenciaBanco = FirebaseBanco.getFirebaseBanco().child("condominios");

        valueEventListenerCondominio = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot condominiosId : dataSnapshot.getChildren()){

                    Condominio condominio = condominiosId.getValue(Condominio.class);
                    condominio.setId(condominiosId.getKey());
                    condominioLista.add(condominio);

                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Condominio condominioSelecionado = (Condominio) condominiumSpinner.getSelectedItem();

                Preferencias preferencias = new Preferencias(CondominioActivity.this);
                String idUsuarioLogado = preferencias.getIdentificador();

                referenciaBanco = FirebaseBanco.getFirebaseBanco();

                salvaDadosFirebase(condominioSelecionado, idUsuarioLogado);

                inserirCondominioArquivoPreferencias(condominioSelecionado.getId());

                abrirTelaPrincipal();

            }
        });


        //dataAdapter = new CondominioAdapter(this, condominioLista);
        //condominiumSpinner.setAdapter(dataAdapter);
        dataAdapter = new ArrayAdapter<Condominio>(this, R.layout.item_spinner_condominios, condominioLista);
        dataAdapter.setDropDownViewResource(R.layout.dropdown_spinner_condominios);
        condominiumSpinner.setAdapter(dataAdapter);

    }

    private void salvaDadosFirebase(Condominio condominioSelecionado, String idUsuarioLogado) {

        //Salva usuário no condomínio
        condominioSelecionado.salvaUsuario(referenciaBanco, idUsuarioLogado);

        //Salva condominio no usuário
        Usuario usuario = new Usuario();
        usuario.setId(idUsuarioLogado);
        usuario.setCondominio(condominioSelecionado.getId());
        usuario.salvaCondominio(referenciaBanco);

    }

    private void abrirTelaPrincipal(){

        Intent intent = new Intent(CondominioActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void inserirCondominioArquivoPreferencias(String cond) {

        Preferencias preferencias = new Preferencias(CondominioActivity.this);
        preferencias.salvarDadosCondominio(cond);

    }

}
