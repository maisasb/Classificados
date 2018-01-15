package com.luna.classificados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView textView;
    public String string1 = "Olá Fulano";
    public String string2 = "Ola, tudo bem?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.texto);
        textView.setText(string1);

        Button botao = (Button)findViewById(R.id.mudaTexto);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ola();
            }
        });

    }

    public void ola(){
        if (textView.getText().toString().equals(string1)){
            textView.setText(string2);
        }else{
            textView.setText(string1);
        }

    }
}
