package com.luna.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;


import com.google.firebase.auth.FirebaseAuth;
import com.luna.classificados.R;
import com.luna.classificados.adapter.TabAdapter;
import com.luna.classificados.fragment.CadastroNegocioFragment;
import com.luna.classificados.fragment.MainFragment;
import com.luna.classificados.helper.FirebaseAutenticacao;
import com.luna.classificados.utils.TagFragmentEnum;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        // Menu lateral esquerdo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Carrega o fragmento de anuncios e negocios no "conteudo" do main activity
        Fragment fragment = new MainFragment();
        abreFragmento(fragment, TagFragmentEnum.MAIN.toString(),0);

    }

    private void logoutApp() {
        FirebaseAuth autenticacao = FirebaseAutenticacao.getFirebaseAutenticacao();
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_conf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_sair:
                logoutApp();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_anuncios:
                return true;
            case R.id.nav_negocios:
                Fragment fragmentMain = new MainFragment();
                abreFragmento(fragmentMain, TagFragmentEnum.MAIN.toString(), 1);
                return true;
            case R.id.nav_novo_negocio:
                Fragment fragment = new CadastroNegocioFragment();
                abreFragmento(fragment, TagFragmentEnum.NEGOCIO.toString(), null);
                return true;
            case R.id.nav_gerenciar_negocio:
                return true;
            case R.id.nav_configuracoes:
                return true;
            case R.id.nav_sair:
                logoutApp();
                return true;
        }


        return true;
    }

    /*
     * Método que irá cuidar da abertura de todos os fragmentos relacionados
     * à activity Main.
     * data - usado para passar dados da activity para o fragmento
     * se é null, então não tem dados
     */
    private void abreFragmento(Fragment fragmento, String nome, Integer data) {

        if (data != null){
            Bundle bundle = new Bundle();
            bundle.putInt("tabSelecionada", data);
            fragmento.setArguments(bundle);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.conteudo, fragmento, nome);
        fragmentTransaction.commit();

    }
}
