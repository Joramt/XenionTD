package com.example.ecole.xeniontd.entities;

import android.content.Intent;

/**
 * Created by Nicolas on 2016-01-12.
 */
public class Projectile {
    private int id;
    private String nom;
    private int degat;
    private int vitesse;
    private String imgSrc;


    // constructeurs
    public Projectile(int id, String nom, int degat, int vitesse, String imgSrc) {

        this.id = id;
        this.nom = nom;
        this.degat = degat;
        this.vitesse = vitesse;
        this.imgSrc = imgSrc;
    }

    public Projectile() {
    }


    // getters setters
    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDegat() {
        return degat;
    }

    public void setDegat(int degat) {
        this.degat = degat;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }


}
