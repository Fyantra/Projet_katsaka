<%@ page import="objets.events.*, java.util.ArrayList, connect.*" %>

<%
    ArrayList<Controle> lc = Controle.selectAll(null);
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-md-12 my-4">
            <div class="card shadow">
              <div class="card-body">
                <h5 class="card-title">liste des controles</h5>
                <p class="card-text">
                </p>
                <table class="table table-hover">
                  <thead class="thead-dark">
                    <tr>
                      <th>ID controle</th>
                      <th>Date heure</th>
                      <th>anomalies</th>
                    </tr>
                  </thead>
                  <tbody>
                    <% for(Controle ctrl : lc) { %>
                    <tr>
                      <td><%= ctrl.getIdControle() %></td>
                      <td><%= ctrl.getDateHeure() %></td>
                      <td><a href="ToAnomalieControle?idControle=<%= ctrl.getIdControle() %>"><button type="button" class="btn mb-2 btn-outline-danger">voir anomalies</button></a></td>
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