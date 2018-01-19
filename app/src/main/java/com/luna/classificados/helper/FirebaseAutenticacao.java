package com.luna.classificados.helper;

import com.google.firebase.auth.FirebaseAuth;


public class FirebaseAutenticacao {

    private static FirebaseAuth firebaseAutenticacao;

    public static FirebaseAuth getFirebaseAutenticacao(){

        if(firebaseAutenticacao == null){
            firebaseAutenticacao = FirebaseAuth.getInstance();
        }

        return firebaseAutenticacao;

    }



}
