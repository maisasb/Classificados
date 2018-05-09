package com.luna.classificados.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.luna.classificados.R;
import com.luna.classificados.activity.MainActivity;
import com.luna.classificados.fragment.CadastroNegocioFragment;
import com.luna.classificados.model.Negocio;
import com.luna.classificados.utils.TagFragmentEnum;

import java.util.List;

/**
 * Created by Maisa on 23/04/2018.
 */

public class GerenciarNegocioAdapter extends BaseAdapter {

    private List<Negocio> negocios;
    private MainActivity activity;

    public GerenciarNegocioAdapter(List<Negocio> negocios, Activity activity) {
        this.negocios = negocios;
        this.activity = (MainActivity) activity;
    }

    @Override
    public int getCount() {
        return negocios.size();
    }

    @Override
    public Negocio getItem(int position) {
        return negocios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.item_gerenciar_negocio_adapter, parent, false);

        final Negocio negocio = negocios.get(position);

        TextView textNome = view.findViewById(R.id.nome);
        TextView textDescricao = view.findViewById(R.id.descricao);
        Switch switchItemNegocio = view.findViewById(R.id.switchItemNegocio);

        textNome.setText(negocio.getNome());
        textDescricao.setText(negocio.getDescBreve());
        switchItemNegocio.setChecked(negocio.getStatus());

        switchItemNegocio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                negocio.setStatus(isChecked);
                negocio.salvar();

            }
        });


        Button editar = view.findViewById(R.id.editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Negocio negocio = getItem(position);

                Fragment fragmentNegocio = new CadastroNegocioFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("negocio",negocio);
                bundle.putString("status", "EDITAR");
                fragmentNegocio.setArguments(bundle);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteudo, fragmentNegocio, TagFragmentEnum.CAD_NEGOCIO.toString());

                activity.getSupportFragmentManager().popBackStack();
                fragmentTransaction.commit();
            }
        });

        Button remover = view.findViewById(R.id.remover);
        //TODO remover

        return view;
    }
}
