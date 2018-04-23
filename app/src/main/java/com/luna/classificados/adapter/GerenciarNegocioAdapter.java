package com.luna.classificados.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luna.classificados.R;
import com.luna.classificados.model.Negocio;

import java.util.List;

/**
 * Created by Maisa on 23/04/2018.
 */

public class GerenciarNegocioAdapter extends BaseAdapter {

    private List<Negocio> negocios;
    private Activity activity;
    private String nome;
    private String descricao;

    public GerenciarNegocioAdapter(List<Negocio> negocios, Activity activity) {
        this.negocios = negocios;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return negocios.size();
    }

    @Override
    public Object getItem(int position) {
        return negocios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.item_gerenciar_negocio_adapter, parent, false);

        Negocio negocio = negocios.get(position);

        TextView textNome = view.findViewById(R.id.nome);
        TextView textDescricao = view.findViewById(R.id.descricao);

        textNome.setText(negocio.getNome());
        textDescricao.setText(negocio.getDescricao());

        return view;
    }
}
