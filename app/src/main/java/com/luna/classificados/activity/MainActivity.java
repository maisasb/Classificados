package com.luna.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.luna.classificados.R;
import com.luna.classificados.fragment.CadastroNegocioFragment;
import com.luna.classificados.fragment.MainFragment;
import com.luna.classificados.helper.FirebaseAutenticacao;
import com.luna.classificados.helper.FirebaseBanco;
import com.luna.classificados.helper.Preferencias;
import com.luna.classificados.model.Condominio;
import com.luna.classificados.model.Usuario;
import com.luna.classificados.utils.TagFragmentEnum;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    public ValueEventListener valueEventListenerUsuario;
    public NavigationView navigationView;


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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Preferencias preferencias = new Preferencias(this);
        String idUsuario = preferencias.getIdentificador();

        DatabaseReference referenciaBanco = FirebaseBanco.getFirebaseBanco().child("usuarios").child(idUsuario);

        valueEventListenerUsuario = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               Usuario usuario = dataSnapshot.getValue(Usuario.class);
                View hView = navigationView.getHeaderView(0);
                TextView header_user = hView.findViewById(R.id.header_user);
                header_user.setText(usuario.getNome());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
//


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
                abreFragmento(fragment, TagFragmentEnum.CAD_NEGOCIO.toString(), null);
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
    private void abreFragmento(Fragment fragmento, String nome, Integer dados) {

        if (dados != null){
            Bundle bundle = new Bundle();
            bundle.putInt("tabSelecionada", dados);
            fragmento.setArguments(bundle);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.conteudo, fragmento, nome);
        fragmentTransaction.commit();

    }
}
