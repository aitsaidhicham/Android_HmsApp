package com.dev.hms;

public class info {
    private String localisation;
    private String prix_par_nuit;
    private String wilaya;
    private String Rating;
    private String nom;

    public  info(){

    }

    public info(String localisation, String prix_par_nuit, String wilaya, String rating, String nom) {
        this.localisation = localisation;
        this.prix_par_nuit = prix_par_nuit;
        this.wilaya = wilaya;
        Rating = rating;
        this.nom = nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getPrix_par_nuit() {
        return prix_par_nuit;
    }

    public void setPrix_par_nuit(String prix_par_nuit) {
        this.prix_par_nuit = prix_par_nuit;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public CharSequence getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
