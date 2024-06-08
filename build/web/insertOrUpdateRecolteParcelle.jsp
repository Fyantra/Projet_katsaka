<%@page import="objets.events.RecolteParcelle"%>
<%@page import="objets.events.Recolte"%>
<%@ page import="objets.infrastructures.*, java.util.ArrayList, connect.*" %>

<%
    Recolte recolte = (Recolte) request.getAttribute("recolte");
    ArrayList<Parcelle> lp = new Parcelle().select(new Connect().getConnectionPgsql(), true);
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <h2 class="page-title">Inserer ou mettre a jour une recolte parcelle du <%= recolte.getDateHeure() %></h2>
    <form action="insertOrUpdateRecolte2">
        <input type="hidden" id="idRecolte" class="form-control" name="idRecolte" value="<%= recolte.getIdRecolte() %>"> 
        <div class="row">
            <% for (Parcelle p : lp) {
                    RecolteParcelle rp = recolte.isAlreadySaved(p);
                    if (rp == null) {
            %>
            <div class="card shadow mb-4 mr-2 col-md-5">
                <div class="row">
                    <div class="col-md-6">
                        <div class="card-header">
                            <p><strong class="card-title">Parcel <%= p.getDescription()%></strong></p>
                            <p><strong class="card-title">Responsable <%= p.getResponsable().getNom()%></strong></p>
                        </div>
                    </div>
                    <div class="col-md-6" style="display: flex; flex-direction: column; justify-content: center">
                        <div class="form-group mb-3">
                            <label for="save<%= p.getIdParcelle()%>">Enregistrer ?</label>
                            <select id="save" name="save<%= p.getIdParcelle()%>">
                                <option value="1">oui</option>
                                <option value="0">non</option>
                            </select>

                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group mb-3">
                                <label for="nbOs">Nombre os de mais total</label>
                                <input type="number" id="nbOsTotal" class="form-control" name="nbOsTotal<%= p.getIdParcelle()%>"  min="0" step="0.01">
                            </div>
                            <div class="form-group mb-3">
                                <label for="nbOs">Poids total des os de mais</label>
                                <input type="number" id="poidstotal" class="form-control" name="poidstotal<%= p.getIdParcelle()%>"  min="0" step="0.01">
                            </div>
                            <div class="form-group mb-3">
                                <label for="lgOs">Longueur des os de mais</label>
                                <input type="number" id="lgOs" class="form-control" name="lgOs<%= p.getIdParcelle()%>"  min="0" step="0.01">
                            </div>
                        </div> <!-- /.col -->
                    </div>
                </div>
            </div>
            <% } else { %>
            <input type="hidden" id="idRecolteParcelle" class="form-control" name="idRecolteParcelle<%= p.getIdParcelle()%>" value="<%= rp.getIdRecolteParcelle() %>"> 
            <div class="card shadow mb-4 mr-2 col-md-5">
                <div class="row">
                    <div class="col-md-6">
                        <div class="card-header">
                            <p><strong class="card-title">Parcel <%= p.getDescription()%></strong></p>
                            <p><strong class="card-title">Responsable <%= p.getResponsable().getNom()%></strong></p>
                        </div>
                    </div>
                    <div class="col-md-6" style="display: flex; flex-direction: column; justify-content: center">
                        <div class="form-group mb-3">
                            <label for="save<%= p.getIdParcelle()%>">Mettre a jour ?</label>
                            <select id="save" name="save<%= p.getIdParcelle()%>">
                                <option value="0">non</option>
                                <option value="1">oui</option>
                            </select>

                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group mb-3">
                                <label for="nbOs">Nombre os de mais total</label>
                                <input type="number" id="nbOsTotal" class="form-control" name="nbOsTotal<%= p.getIdParcelle()%>"  min="0" step="0.01" value="<%= rp.getNombreOsTotal() %>">
                            </div>
                            <div class="form-group mb-3">
                                <label for="nbOs">Poids total des os de mais</label>
                                <input type="number" id="poidstotal" class="form-control" name="poidstotal<%= p.getIdParcelle()%>"  min="0" step="0.01" value="<%= rp.getPoidsTotal() %>">
                            </div>
                            <div class="form-group mb-3">
                                <label for="lgOs">Longueur des os de mais</label>
                                <input type="number" id="lgOs" class="form-control" name="lgOs<%= p.getIdParcelle()%>"  min="0" step="0.01" value="<%= rp.getLongueurOsParTige() %>">
                            </div>
                        </div> <!-- /.col -->
                    </div>
                </div>
            </div>
            <% }%>
            <% }%>
        </div>
        <div class="row">
            <button type="submit" class="btn mb-2 btn-success">Inserer</button>
        </div>
    </form>
</div>
<%@ include file = "footer.html" %>