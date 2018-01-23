package com.luna.classificados.helper;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseBanco {

    private static DatabaseReference firebaseBanco;

    public static DatabaseReference getFirebaseBanco(){

        if(firebaseBanco == null){
            firebaseBanco = FirebaseDatabase.getInstance().getReference();
        }

        return firebaseBanco;

    }

}
