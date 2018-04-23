package com.luna.classificados.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.luna.classificados.R;
import com.luna.classificados.activity.CondominioActivity;
import com.luna.classificados.activity.MainActivity;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Categoria;
import com.luna.classificados.model.Condominio;
import com.luna.classificados.model.Negocio;
import com.luna.classificados.model.Usuario;
import com.luna.classificados.utils.TagFragmentEnum;

import java.util.ArrayList;
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
    public ArrayList<Categoria> categoriaLista;
    public ArrayAdapter dataAdapter;
    public DatabaseReference referenciaBanco;
    public ValueEventListener valueEventListenerCategoria;


    @Override
    public void onStop() {
        super.onStop();
        referenciaBanco.removeEventListener(valueEventListenerCategoria);
    }

    @Override
    public void onStart() {
        super.onStart();
        referenciaBanco.addListenerForSingleValueEvent(valueEventListenerCategoria);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_negocio, container, false);

        categoriaLista = new ArrayList<>();

        referenciaBanco = FirebaseBanco.getFirebaseBanco().child("categorias");

        nomeNegocio = view.findViewById(R.id.nomeNegocio);
        descBreveNegocio = view.findViewById(R.id.descBreveNegocio);
        descNegocio = view.findViewById(R.id.descNegocio);
        categoriaNegocio = view.findViewById(R.id.spinnerCategoria);
        contatoNegocio = view.findViewById(R.id.contatoNegocio);
        categoriaNegocio = view.findViewById(R.id.spinnerCategoria);
        botaoCadastrar = view.findViewById(R.id.botaoCadastrar);


        valueEventListenerCategoria = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot categoriasId : dataSnapshot.getChildren()){

                    Categoria categoria = categoriasId.getValue(Categoria.class);
                    if (categoria != null){
                        categoria.setId(categoriasId.getKey());
                        categoriaLista.add(categoria);
                    }

                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaCampos()){

                        Negocio negocio = new Negocio();
                        negocio.setNome(nomeNegocio.getText().toString());
                        negocio.setDescBreve(descBreveNegocio.getText().toString());
                        negocio.setDescricao(descNegocio.getText().toString());
                        negocio.setContato(contatoNegocio.getText().toString());
                        Categoria categoria = (Categoria) categoriaNegocio.getSelectedItem();
                        negocio.setCategoria(categoria.getId());

                        Calendar timeStamp = Calendar.getInstance();
                        String idNegocio = String.valueOf(timeStamp.getTimeInMillis());
                        negocio.setId(idNegocio);

                        Preferencias preferencias = new Preferencias(getActivity());
                        String idUsuario = preferencias.getIdentificador();
                        negocio.setUsuario(idUsuario);
                        negocio.salvar();

                        //Salva negocio no usuário
                        Usuario usuario = new Usuario();
                        usuario.setId(idUsuario);
                        usuario.salvaNegocio(idNegocio);

                        String idCondominio = preferencias.getCondominio();
                        Condominio condominio = new Condominio();
                        condominio.setId(idCondominio);
                        condominio.salvaNegocio(idNegocio);

                        abrirTelaGerenciamento();
                    }
            }
        });

        dataAdapter = new ArrayAdapter(getActivity(), R.layout.item_spinner, categoriaLista);
        dataAdapter.setDropDownViewResource(R.layout.dropdown_spinner);
        categoriaNegocio.setAdapter(dataAdapter);

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
        if (contatoNegocio.getText().length() == 0){
            return false;
        }
        return true;
    }

    private void abrirTelaGerenciamento(){

        Fragment fragmentNegocio = new GerenciarNegociosFragment();
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.conteudo, fragmentNegocio, TagFragmentEnum.GERENCIA_NEGOCIO.toString());

        getActivity().getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();



    }


}

