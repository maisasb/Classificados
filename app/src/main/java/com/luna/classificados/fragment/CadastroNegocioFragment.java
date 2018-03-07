package com.luna.classificados.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.luna.classificados.R;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Negocio;

import java.util.Calendar;

/**
 * Created by Maisa on 01/02/2018.
 */

public class CadastroNegocioFragment extends Fragment {

    public EditText nomeNegocio;
    public EditText descBreveNegocio;
    public EditText descNegocio;
    public EditText contatoNegocio;
    public Spinner categoriaNegocio;
    public Button botaoCadastrar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_negocio, container, false);

        nomeNegocio = view.findViewById(R.id.nomeNegocio);
        descBreveNegocio = view.findViewById(R.id.descBreveNegocio);
        descNegocio = view.findViewById(R.id.descNegocio);
        categoriaNegocio = view.findViewById(R.id.spinnerCategoria);
        contatoNegocio = view.findViewById(R.id.contatoNegocio);

        botaoCadastrar = view.findViewById(R.id.botaoCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaCampos()){

                        Negocio negocio = new Negocio();
                        negocio.setNome(nomeNegocio.getText().toString());
                        negocio.setDescBreve(descBreveNegocio.getText().toString());
                        negocio.setDescricao(descNegocio.getText().toString());
                        negocio.setContato(contatoNegocio.getText().toString());
                        //negocio.setCategoria(catNegocio.gett);

                        Calendar timeStamp = Calendar.getInstance();
                        String idNegocio = String.valueOf(timeStamp.getTimeInMillis());
                        negocio.setId(idNegocio);

                        Preferencias preferencias = new Preferencias(getActivity());
                        String idUsuario = preferencias.getIdentificador();
                        negocio.setUsuario(idUsuario);
                        negocio.salvar();

                        DatabaseReference referenciaBanco = FirebaseBanco.getFirebaseBanco().child("usuarios").child(idUsuario);
                        referenciaBanco.child("negocios").child(idNegocio).setValue(true);


                    }
            }
        });

        return view;

    }


    private boolean validaCampos() {

        if (nomeNegocio.getText().length() == 0){
            return false;
        }
        if (descBreveNegocio.getText().length() == 0){
            return false;
        }
        if (descNegocio.getText().length() == 0){
            return false;
        }
     /*   if (spinnerCategoria. == 0){
            return false;
        }*/
        if (contatoNegocio.getText().length() == 0){
            return false;
        }
        return true;
    }

}

