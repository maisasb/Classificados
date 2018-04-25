package com.luna.classificados.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.luna.classificados.R;
import com.luna.classificados.adapter.GerenciarNegocioAdapter;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Condominio;
import com.luna.classificados.model.Negocio;

import java.util.ArrayList;

/**
 * Created by Maisa on 08/03/2018.
 */

public class GerenciarNegociosFragment extends Fragment {

    public ListView listViewNegocios;
    public ValueEventListener valueEventListenerNegocios;
    public DatabaseReference referenciaBanco;
    public ArrayList<Negocio> negocioLista;
    public GerenciarNegocioAdapter dataAdapter;

    @Override
    public void onStop() {
        super.onStop();
        referenciaBanco.removeEventListener(valueEventListenerNegocios);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_gerenciar_negocios, container, false);

        listViewNegocios = view.findViewById(R.id.listViewNegocios);
        negocioLista = new ArrayList<Negocio>();

        Preferencias preferencias = new Preferencias(getActivity());
        String idUsuario = preferencias.getIdentificador();

        referenciaBanco = FirebaseBanco.getFirebaseBanco().child("usuarios").child(idUsuario).child("negocios");

        referenciaBanco.addListenerForSingleValueEvent(valueEventListenerNegocios = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Para cada negocio encontrado no usuário
                for(DataSnapshot negociosId : dataSnapshot.getChildren()){

                    //Pega a referência do banco do negócio
                    DatabaseReference referenciaNegocio = FirebaseBanco.getFirebaseBanco().child("negocios").child(negociosId.getKey());

                    ValueEventListener eventListenter;

                    //Cria um listener para cada negócio encontrado
                    referenciaNegocio.addListenerForSingleValueEvent(eventListenter = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Recebe a entidade negocio
                            Negocio negocioEntidade = dataSnapshot.getValue(Negocio.class);
                            if (negocioEntidade != null){
                                negocioEntidade.setId(dataSnapshot.getKey());
                                negocioLista.add(negocioEntidade);
                                dataAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    referenciaNegocio.removeEventListener(eventListenter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Exibindo a lista no adapter personalizado
        dataAdapter = new GerenciarNegocioAdapter(negocioLista, getActivity());
        listViewNegocios.setAdapter(dataAdapter);

        return view;
    }

}
