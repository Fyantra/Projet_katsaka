<%@page import="objets.anomalies.AnomalieControle"%>
<%@ page import="objets.events.*, java.util.ArrayList, connect.*" %>

<%
    Controle c = (Controle) request.getAttribute("controleAnomalie");
    ArrayList<ControleParcelle> lcp = c.getlControleParcelles();
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <div class="row">
        <h5 class="card-title">Controle du <%= c.getDateHeure() %></h5>
        <% for(ControleParcelle cp : lcp) { 
            AnomalieControle anoCtrl = cp.getAnomalieControle();
        %>
        <div class="col-md-12 my-4">
            <div class="card shadow">
              <div class="card-body">
                <p class="card-text">
                    Parcelle <%= cp.getParcelle().getDescription() %>
                </p>
                <p class="card-text">
                    Responsable <%= cp.getParcelle().getResponsable().getNom() %>
                </p>
                <table class="table table-hover">
                  <thead class="thead-dark">
                    <tr>
                      <th>Criteres</th>
                      <th>Precendent</th>
                      <th>Actuel</th>
                      <th>Anomalie</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>Nombre tiges</td>
                      <td><%= anoCtrl.getCpAvant().getNombreTiges() %></td>
                      <td><%= anoCtrl.getCpActuel().getNombreTiges() %></td>
                      <td><%= anoCtrl.getPhraseNombreTiges() %></td>
                    </tr>
                    <tr>
                      <td>Nombre os par tiges en moyenne</td>
                      <td><%= anoCtrl.getCpAvant().getNombreOsParTige()%></td>
                      <td><%= anoCtrl.getCpActuel().getNombreOsParTige() %></td>
                      <td><%= anoCtrl.getPhraseNombreOsParTige()%></td>
                    </tr>
                    <tr>
                      <td>Longueur os par tiges en moyenne</td>
                      <td><%= anoCtrl.getCpAvant().getLongueurOsParTige()%></td>
                      <td><%= anoCtrl.getCpActuel().getLongueurOsParTige() %></td>
                      <td><%= anoCtrl.getPhraseLongueurOsParTige()%></td>
                    </tr>
                    <tr>
                      <td>Couleur</td>
                      <td><%= anoCtrl.getCpAvant().getCouleur()%></td>
                      <td><%= anoCtrl.getCpActuel().getCouleur() %></td>
                      <td><%= anoCtrl.getPhraseCouleur() %></td>
                    </tr>
                  </tbody>
                </table>
                    <div>
                        <p>
                            Croissance en moyenne de <%= cp.getParcelle().getDescription() %> : <%= cp.getPourcentageCroiss() %> %
                        </p>
                        <p>
                            Croissance en moyenne dans les autres parcelles : <%= cp.getPourcentageCroissMoyenne() %> %
                        </p>
                        <p>
                            Anomalie : <%= anoCtrl.getPhraseCroissanceMoyenneOsParTige() %>
                        </p>
                    </div>
              </div>
            </div>
          </div>
        <% } %>
    </div> <!-- .row -->
</div>
<%@ include file = "footer.html" %>