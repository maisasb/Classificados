package com.luna.classificados.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.luna.classificados.R;
import com.luna.classificados.model.Negocio;

/**
 * Created by natalia.vaz on 24/04/2018.
 */

public class DetalhesNegocioFragment extends Fragment {

    public TextView nomeNegocio;
    public TextView descricaoBreveNegocio;
    public TextView descricaoNegocio;
    public TextView statusNegocio;
    public boolean status;


    public DetalhesNegocioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhes_negocio, container, false);

        Bundle bundle = getArguments();
        Negocio negocio = (Negocio) bundle.getSerializable("negocio");

        nomeNegocio = view.findViewById(R.id.nome_negocio);
        descricaoBreveNegocio = view.findViewById(R.id.descBreveNegocio);
        descricaoNegocio = view.findViewById(R.id.descricao_negocio);
        statusNegocio = view.findViewById(R.id.statusNegocio);


        nomeNegocio.setText(negocio.getNome());
        descricaoBreveNegocio.setText(negocio.getDescBreve());
        descricaoNegocio.setText(negocio.getDescricao());

        status = negocio.getStatus();
        if (status){
            statusNegocio.setText("Negócio Aberto");
        }
            else{
            statusNegocio.setText("Negócio Fechado");
        }




        //TODO completar com layout do status do negocio

        return view;
    }





}
