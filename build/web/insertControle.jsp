<%@ page import="objets.infrastructures.*, java.util.ArrayList, connect.*" %>

<% 
    ArrayList<Parcelle> lp = new Parcelle().select(new Connect().getConnectionPgsql(), true);
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <h2 class="page-title">Inserer un controle</h2>
    <form action="InsererControle">
        <div class="card shadow mb-4 col-md-6">
            <div class="card-body">
                <div class="card-header">
                    <p><strong class="card-title">Date et heure du controle</strong></p>
                </div>
            </div>
            <div class="form-row mb-3">
                <div class="col-md-6 mb-3">
                    <label for="date-input1">Date</label>
                    <div class="input-group">
                        <input type="date" id="date-input1" name="dateControle" aria-describedby="button-addon2" required>
                    </div>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="example-time">Heure</label>
                    <input id="example-time" type="time" name="heureControle" required="">
                </div>
            </div>
        </div>
        <div class="row">
            <% for (Parcelle p : lp) { %>
        <div class="card shadow mb-4 mr-2 col-md-5">
            <div class="card-header">
                <p><strong class="card-title">Parcel <%= p.getDescription() %></strong></p>
                <p><strong class="card-title">Responsable <%= p.getResponsable().getNom() %></strong></p>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group mb-3">
                            <label for="nbTiges">Nombre tiges de mais</label>
                            <input type="number" id="nbTiges" class="form-control" name="nbTiges<%= p.getIdParcelle() %>" min="0" required="" step="0.01">
                        </div>
                        <div class="form-group mb-3">
                            <label for="nbOs">Nombre os de mais par tige</label>
                            <input type="number" id="nbOs" class="form-control" name="nbOs<%= p.getIdParcelle() %>"  min="0" required="" step="0.01">
                        </div>
                        <div class="form-group mb-3">
                            <label for="lgOs">Longueur des os de mais</label>
                            <input type="number" id="lgOs" class="form-control" name="lgOs<%= p.getIdParcelle() %>"  min="0" required="" step="0.01">
                        </div>
                        <div class="form-group mb-3">
                            <label for="couleur">Couleur</label>
                            <input type="number" id="couleur" class="form-control" name="couleur<%= p.getIdParcelle() %>"  min="0" required="" step="0.01">
                        </div>
                    </div> <!-- /.col -->
                </div>
            </div>
        </div>
        <% } %>
        </div>
        <div class="row">
            <button type="submit" class="btn mb-2 btn-success">Inserer</button>
        </div>
    </form>
</div>
<%@ include file = "footer.html" %>