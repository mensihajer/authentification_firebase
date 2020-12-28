package com.hajer_mensi.authentification_firebase;

public class User {
    public String nom ,prenom  ,email;
    public User(String nom, String prenom, String email){

    }
    public User (String nom, String prenom ,String email,String password){
        this.nom =nom;
        this.prenom=prenom;
        this.email=email;

    }

}
