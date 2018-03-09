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
import com.luna.classificados.helper.FirebaseBanco;
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
    public ArrayAdapter dataAdapter;

    @Override
    public void onStop() {
        super.onStop();
        referenciaBanco.removeEventListener(valueEventListenerNegocios);
    }

    @Override
    public void onStart() {
        super.onStart();
        referenciaBanco.addListenerForSingleValueEvent(valueEventListenerNegocios);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_gerenciar_negocios, container, false);

        listViewNegocios = view.findViewById(R.id.listViewNegocios);
        negocioLista = new ArrayList<Negocio>();

        referenciaBanco = FirebaseBanco.getFirebaseBanco().child("condominios");

        valueEventListenerNegocios = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot condominiosId : dataSnapshot.getChildren()){

                    Negocio negocio = condominiosId.getValue(Negocio.class);
                    if (negocio != null){
                        negocio.setId(condominiosId.getKey());
                        negocioLista.add(negocio);
                    }

                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        dataAdapter = new ArrayAdapter<Negocio>(getActivity(), android.R.layout.simple_list_item_1, negocioLista);
        listViewNegocios.setAdapter(dataAdapter);

        return view;
    }

}
