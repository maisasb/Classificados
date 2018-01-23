package com.luna.classificados.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.luna.classificados.model.Condominio;



import java.util.ArrayList;


public class CondominioAdapter extends BaseAdapter implements SpinnerAdapter{
    private Activity activity;
    private ArrayList<Condominio> condominios;

    public CondominioAdapter(Activity activity, ArrayList<Condominio> condominios){
        this.activity = activity;
        this.condominios = condominios;
    }

    public int getCount() {
        return condominios.size();
    }

    public Object getItem(int position) {
        return condominios.get(position);
    }

    public long getItemId(int position) {
        return Long.parseLong(condominios.get(position).getId());
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View spinView;
        if( convertView == null ){
            LayoutInflater inflater = activity.getLayoutInflater();
            spinView = inflater.inflate(android.R.layout.simple_spinner_item, null);
        } else {
            spinView = convertView;
        }
        TextView t1 = (TextView) spinView.findViewById(android.R.id.text1);
        t1.setText(condominios.get(position).getNome());

        return spinView;
    }
}

