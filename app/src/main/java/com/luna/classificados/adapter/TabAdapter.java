package com.luna.classificados.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.luna.classificados.R;
import com.luna.classificados.fragment.AnunciosFragment;
import com.luna.classificados.fragment.NegociosFragment;


public class TabAdapter extends FragmentStatePagerAdapter{

    public String[] tituloAbas = {"ANÚNCIOS", "NEGÓCIOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch ( position ){
            case 0:
                fragment = new AnunciosFragment();
                break;
            case 1:
                fragment = new NegociosFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[ position ];
    }
}
