/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objets.infrastructures;

import annot.FieldAnnotation;
import annot.FkAnnotation;
import annot.TableAnnotation;
import dao.ObjetBdd;
import objets.humains.Responsable;

/**
 *
 * @author Fy
 */
@TableAnnotation(nomTable = "parcelles")
public class Parcelle extends ObjetBdd {
    int idParcelle = -1;
    String description;
    @FkAnnotation(fieldName = "idResponsable")
            @FieldAnnotation(nomColonne = "idResponsable", miditra = true)
    Responsable responsable;
    double longueur = -1;
    double largeur = -1;
    double limiteControleLongueur = -1;

    public double getLimiteControleLongueur() {
        return limiteControleLongueur;
    }

    public void setLimiteControleLongueur(double limiteControleLongueur) {
        this.limiteControleLongueur = limiteControleLongueur;
    }
    
    public int getIdParcelle() {
        return idParcelle;
    }

    public void setIdParcelle(int idParcelle) {
        this.idParcelle = idParcelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }
    
    
}
