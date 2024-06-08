<%@ page import="objets.events.*, java.util.ArrayList, connect.*" %>

<%
    ArrayList<Recolte> lc = Recolte.selectAll(null);
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-md-12 my-4">
            <div class="card shadow">
              <div class="card-body">
                <h5 class="card-title">liste des Recoltes</h5>
                <p class="card-text">
                </p>
                <table class="table table-hover">
                  <thead class="thead-dark">
                    <tr>
                      <th>ID Recolte</th>
                      <th>Date heure</th>
                      <th></th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    <% for(Recolte recolte : lc) { %>
                    <tr>
                      <td><%= recolte.getIdRecolte() %></td>
                      <td><%= recolte.getDateHeure() %></td>
                      <td><a href="InsertOrUpdateRecolteParcelle?idRecolte=<%= recolte.getIdRecolte() %>"><button type="button" class="btn mb-2 btn-outline-info">enregistrer nouvelle parcelle</button></a></td>
                      <td><a href="ToDetailRecolte?idRecolte=<%= recolte.getIdRecolte() %>"><button type="button" class="btn mb-2 btn-outline-warning">voir prevision et anomalie</button></a></td>
                    </tr>
                    <% } %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
    </div> <!-- .row -->
</div>
<%@ include file = "footer.html" %>