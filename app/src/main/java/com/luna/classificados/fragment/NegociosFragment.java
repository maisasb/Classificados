package com.luna.classificados.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.luna.classificados.R;
import com.luna.classificados.adapter.ListaNegociosAdapter;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NegociosFragment extends Fragment {

    public ListView listViewNegocios;
    public List<Negocio> negocios;
    public ListaNegociosAdapter listaNegociosAdapter;
    public DatabaseReference referenciaBanco;
    public ValueEventListener valueEventListenerNegocios;



    public NegociosFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_negocios, container, false);
        listViewNegocios = view.findViewById(R.id.listViewNegocios);

        //Exibindo a lista no adapter personalizado
        negocios = new ArrayList<>();
        listaNegociosAdapter = new ListaNegociosAdapter(negocios,getActivity());
        listViewNegocios.setAdapter(listaNegociosAdapter);

        Preferencias preferencias = new Preferencias(getActivity());
        String idCondominio = preferencias.getCondominio();

        referenciaBanco = FirebaseBanco.getFirebaseBanco().child("condominios").child(idCondominio).child("negocios");

        referenciaBanco.addValueEventListener(valueEventListenerNegocios = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Para cada negocio encontrado no condominio
                for (DataSnapshot negociosId : dataSnapshot.getChildren()) {

                    //Pega a referência do banco do negócio
                    DatabaseReference referenciaNegocio = FirebaseBanco.getFirebaseBanco().child("negocios").child(negociosId.getKey());

                    ValueEventListener eventListener;

                    //Cria um listener para cada negócio encontrado
                    referenciaNegocio.addListenerForSingleValueEvent(eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Recebe a entidade negocio
                            Negocio negocioEntidade = dataSnapshot.getValue(Negocio.class);
                            if (negocioEntidade != null) {
                                negocioEntidade.setId(dataSnapshot.getKey());
                                negocios.add(negocioEntidade);
                                listaNegociosAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    referenciaNegocio.removeEventListener(eventListener);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;

    }

}
