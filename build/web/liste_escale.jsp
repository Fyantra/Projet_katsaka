<%@ page import="objets.infrastructure.*, objets.event.*, objets.finance.*, fonctions.*" %>

<% 
    Quai [] lq = Quai.getAllQuai(null);
    Escale [] lesc = new Escale[0];
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-12">
            <div class="row align-items-center my-4">
                <div class="col">
                    <h2 class="h3 mb-0 page-title">Liste des escales</h2>
                </div>
            </div>
            <!-- CONTENT -->
            <div class="row my-4">
                <!-- Ordre de priorite -->
                <div class="col-md-12">
                    <div class="card mb-4 shadow">
                        <div class="card-body">
                            <p><h2 class="h2 mb-0 page-title">Escales</h2></p> 
                            <div class="form-row">
                                <div class="col-md-12 mb-4">
                                    <div class="card-body" style="height: 100%; overflow-y: scroll;">
                                        <table class="table table-sm table-hover table-borderless">
                                            <thead>
                                                <tr>
                                                    <th>Escale</th>
                                                    <th>Quai</th>
                                                    <th>ID Navire</th>
                                                    <th>description</th>
                                                    <th>client</th>
                                                    <th>entree</th>
                                                    <th>sortie</th>
                                                    <th>cout (Ar)</th>
                                                    <th>cout (Euro)</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% 
                                                    for(int i = 0; i < lq.length; i++) {
                                                    lesc = lq[i].getEscales();
                                                    for(int j = 0; j < lesc.length; j++) { 
                                                %>
                                                <tr>
                                                    <td><%= (j+1) %></td>
                                                    <td><%= lq[i].getDescription() %></td>
                                                    <td><%= lesc[j].getNavire().getIdentifiant() %></td>
                                                    <td><%= lesc[j].getNavire().getDescription() %></td>
                                                    <td><%= lesc[j].getNavire().getClient().getDescription() %></td>
                                                    <td><%= lesc[j].getEntree() %></td>
                                                    <td><%= lesc[j].getSortie() %></td>
                                                    <td><%= lesc[j].getMontant().getAriary() %></td>
                                                    <td><%= lesc[j].getMontant().getEuro() %></td>
                                                </tr>
                                                <% } %>
                                                <% } %>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> <!-- .card -->
                </div> <!-- .col-md-->
                <!-- FIN Ordre de priorite -->
                <div class="col-md-12">
                    <div class="mb-2" style="width: 50%; display: flex; flex-direction: row; justify-content: space-evenly; margin: 0 auto;">
                        <a href="prevision_choix_navire.jsp"><button type="button" class="btn mb-2 btn-outline-warning">Retour</button></a> 
                    </div>
                </div>
                </form>
            </div>
            <!-- FIN CONTENT -->
        </div> <!-- .col-12 -->
    </div> <!-- .row -->
</div>
<%@ include file = "footer.html" %>