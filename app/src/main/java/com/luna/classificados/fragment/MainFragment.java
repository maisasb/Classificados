package com.luna.classificados.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luna.classificados.R;
import com.luna.classificados.adapter.TabAdapter;

/**
 * Created by Maisa on 01/02/2018.
 */

public class MainFragment extends Fragment {

    private TabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        int tabSelecionada = getArguments().getInt("tabSelecionada");

        //Sliding Tabs
        slidingTabLayout = (TabLayout) view.findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) view.findViewById(R.id.vp_pagina);

        //Configurar adapter
        TabAdapter tabAdapter = new TabAdapter( getFragmentManager() );
        viewPager.setAdapter( tabAdapter );

        slidingTabLayout.setupWithViewPager( viewPager );

        TabLayout.Tab tab = slidingTabLayout.getTabAt(tabSelecionada);
        if (tab != null){
            tab.select();
        }

        return view;
    }
}
