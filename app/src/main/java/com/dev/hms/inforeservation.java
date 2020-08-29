package com.dev.hms;

public class inforeservation {

    private String nomHotel;
    private String dure;
    private String prix;
    private String datedebut;
    private String nom;

    public  inforeservation(){

    }

    public inforeservation(String nomHotel, String dure, String prix, String datedebut, String nom) {
        this.nomHotel = nomHotel;
        this.dure = dure;
        this.prix = prix;
        this.datedebut = datedebut;
        this.nom = nom;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
