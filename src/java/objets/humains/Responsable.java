/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objets.humains;

import annot.TableAnnotation;
import annot.TsyMiditraAnnotation;
import dao.ObjetBdd;
import java.util.ArrayList;
import objets.infrastructures.Parcelle;

/**
 *
 * @author Fy 
 */
@TableAnnotation(nomTable = "responsables")
public class Responsable extends ObjetBdd { 
    int idResponsable = -1;
    String nom;
    @TsyMiditraAnnotation
    ArrayList<Parcelle> listeParcelles;

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Parcelle> getListeParcelles() {
        return listeParcelles;
    }

    public void setListeParcelles(ArrayList<Parcelle> listeParcelles) {
        this.listeParcelles = listeParcelles;
    }
    
    
}
