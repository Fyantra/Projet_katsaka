/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objets.events;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Fy
 */
public class Prevision {
    int idPrevision = -1;
    Recolte recolte;
    double poidsTotalRecolte = -1;
    
    public void etablirPrevision(Connection connection) throws Exception {
        ArrayList<RecolteParcelle> recolteParcelles = this.getRecolte().getlRecolteParcelles();
        RecolteParcelle firstRp = recolteParcelles.get(0);
        ArrayList<ControleParcelle> lCpLast = ControleParcelle.getLControleParcellesMemeDateHeure(firstRp.getAnomalieRecolte().getDernierControleParcelle().getControle().getDateHeure(), connection);
        
        ArrayList<ControleParcelle> lCpLastRecolte = new ArrayList<>();
        
        for (int i = 0; i < recolteParcelles.size(); i++) {
            RecolteParcelle get = recolteParcelles.get(i);
            ControleParcelle cp = get.getAnomalieRecolte().getDernierControleParcelle();
            lCpLastRecolte.add(cp);
        }
        
        double poidsTotalSur = getPoidsTotalSur();
        
        double poidsUnitaire = getPoidsUnitaireMoyenne();
        double longueur = getLongueurMoyenne();
        
        ArrayList<ControleParcelle> lCpLastNonRecolte = ControleParcelle.getLControleParcellesExcluant(lCpLastRecolte, lCpLast);
        double poidsTotalApprox = 0;
        for (int i = 0; i < lCpLastNonRecolte.size(); i++) {
            ControleParcelle cpNonRecolte = lCpLastNonRecolte.get(i);
            poidsTotalApprox += cpNonRecolte.getPoidsApproximatif(poidsUnitaire, longueur);
        }
        
        this.setPoidsTotalRecolte(poidsTotalSur + poidsTotalApprox);
    }
    
    public double getLongueurMoyenne() throws Exception {
        double nbRecolteParcelle = this.getRecolte().getlRecolteParcelles().size();
        if(nbRecolteParcelle == 0) throw new Exception("Prevision.getLongueurMoyenne : nbRecolteParcelle == 0 ");
        
        double lg = 0;
        for (int i = 0; i < this.getRecolte().getlRecolteParcelles().size(); i++) {
            RecolteParcelle get = this.getRecolte().getlRecolteParcelles().get(i);
            lg += get.getLongueurOsParTige();
        }
        
        return lg / nbRecolteParcelle;
    }
    
    public double getPoidsUnitaireMoyenne() {
        double sommePoidsUnitaire = 0;
        for (int i = 0; i < this.getRecolte().getlRecolteParcelles().size(); i++) {
            RecolteParcelle get = this.getRecolte().getlRecolteParcelles().get(i);
            sommePoidsUnitaire += get.getPoidsUnitaire();
        }
        return sommePoidsUnitaire / this.getRecolte().getlRecolteParcelles().size();
    }
    
    public double getPoidsMoyenne() throws Exception {
        double poidsTotalSur = getPoidsTotalSur();
        double nbRecolteParcelle = this.getRecolte().getlRecolteParcelles().size();
        if(nbRecolteParcelle == 0) throw new Exception("Prevision.getPoidsMoyenne : nbRecolteParcelle == 0 ");
        return poidsTotalSur / nbRecolteParcelle;
    } 
    
    public double getPoidsTotalSur() {
        double poidsTotalSur = 0;
        for (int i = 0; i < this.getRecolte().getlRecolteParcelles().size(); i++) {
            RecolteParcelle get = this.getRecolte().getlRecolteParcelles().get(i);
            poidsTotalSur += get.getPoidsTotal();
        }
        return poidsTotalSur;
    }

    public int getIdPrevision() {
        return idPrevision;
    }

    public void setIdPrevision(int idPrevision) {
        this.idPrevision = idPrevision;
    }

    public Recolte getRecolte() {
        return recolte;
    }

    public void setRecolte(Recolte recolte) {
        this.recolte = recolte;
    }

    public double getPoidsTotalRecolte() {
        return poidsTotalRecolte;
    }

    public void setPoidsTotalRecolte(double poidsTotalRecolte) {
        this.poidsTotalRecolte = poidsTotalRecolte;
    }
}
