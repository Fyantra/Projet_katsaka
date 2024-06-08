<%@page import="objets.anomalies.AnomalieRecolte"%>
<%@ page import="objets.events.*, java.util.ArrayList, connect.*" %>

<%
    Recolte recolte = (Recolte) request.getAttribute("recolte");
    ArrayList<RecolteParcelle> lrp = recolte.getlRecolteParcelles();
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <div class="row">
        <h3 class="card-title"><p>Prevision : <%= recolte.getPrevision().getPoidsTotalRecolte()%> kg au total</p></h3>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <h5 class="card-title"><p>Recolte du <%= recolte.getDateHeure()%></p></h5>
        <% for (RecolteParcelle rp : lrp) {
                AnomalieRecolte anoRecolte = rp.getAnomalieRecolte();
        %>
        <div class="col-md-12 my-4">
            <div class="card shadow">
                <div class="card-body">
                    <p class="card-text">
                        Parcelle <%= rp.getParcelle().getDescription()%>
                    </p>
                    <p class="card-text">
                        Responsable <%= rp.getParcelle().getResponsable().getNom()%>
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
                                <td>Nombre os par tiges en moyenne</td>
                                <td><%= anoRecolte.getDernierControleParcelle().getNombreOsTotalApprox()%></td>
                                <td><%= anoRecolte.getRecolteParcelle().getNombreOsTotal()%></td>
                                <td><%= anoRecolte.getPhraseNombreOsParTige()%></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <% }%>
    </div> <!-- .row -->
</div>
<%@ include file = "footer.html" %>