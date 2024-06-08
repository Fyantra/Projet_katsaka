<%@ page import="objets.infrastructure.*, objets.event.*, objets.finance.*, java.util.ArrayList, fonctions.*" %>

<% 
    Proposition proposition = (Proposition) request.getAttribute("proposition");
    session.setAttribute("newProposition",proposition);
    Quai quaiPropose = proposition.getQuai();
    Prevision prevision = proposition.getPrevision();
    Prestation[] lprestations = prevision.getPrestations();
    Navire navire = prevision.getNavire(); 
    ArrayList<Prestation> lp = new Prestation().select(new connect.ConnectionDeliver().getConnection(), true);
    Quai [] lq = Quai.getAllQuai(null);
    Montant montant = proposition.getEscale().getMontant();
%>

<%@ include file = "header.html" %>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-12">
            <div class="row align-items-center my-4">
                <div class="col">
                    <h2 class="h3 mb-0 page-title">Proposition</h2>
                </div>
            </div>
            <!-- CONTENT -->
            <div class="row my-4">
                <!-- A PROPOS -->
                <div class="col-md-12">
                    <div class="card mb-4 shadow">
                        <div class="card-body my-n3">
                            <div class="row align-items-center">
                                <div class="col-md-6">
                                    <p><h2 class="h2 mb-0 page-title">A propos</h2></p>                                   
                                    <p><h4 class="h4 mb-0 page-title"><%= navire.getDescription() %> - <%= navire.getType().getDescription() %> </h4></p>
                                    <p><h6 class="h6 mb-0"><%= navire.getOrigine().getDescription() %></h6></p>
                                    <p><h5 class="h5 mb-0 page-title">Profondeur : <%= navire.getProfondeur() %> metres</h5></p>
                                    <p><h6 class="h6 mb-0">Client : <%= navire.getClient().getDescription() %></h6></p>
                                </div> <!-- .col -->
                                <div class="col-md-6">
                                    <p><h2 class="h2 mb-0 page-title">Prix estimatif</h2></p>     
                                    <div class="row align-items-center">
                                        <div class="col-md-6 mb-4">
                                            <div class="card shadow border-0">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <div class="col-3 text-center">
                                                            <span class="circle circle-sm bg-primary">

                                                            </span>
                                                        </div>
                                                        <div class="col pr-0">
                                                            <p class="small mb-0">National</p>
                                                            <span class="h3 mb-0"><%= montant.getAriary() %> Ar</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div> 
                                        <div class="col-md-6 mb-4">
                                            <div class="card shadow border-0">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <div class="col-3 text-center">
                                                            <span class="circle circle-sm bg-success-darker">

                                                            </span>
                                                        </div>
                                                        <div class="col pr-0">
                                                            <p class="small mb-0">International</p>
                                                            <span class="h3 mb-0"><%= montant.getEuro() %> Euro</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div> 
                                    </div>
                                </div>
                            </div> <!-- .row -->
                        </div> <!-- .card-body -->
                    </div> <!-- .card -->
                </div> <!-- .col-md-->
                <!-- FIN A PROPOS -->
                <form action="ToProposition" style="width: 100%;">
                    <!-- Prevision d'escale -->
                    <div class="col-md-12">
                        <div class="card mb-4 shadow">
                            <div class="card-body">
                                <p><h2 class="h2 mb-0 page-title">Prevision d'escale</h2></p> 
                                <div class="form-row">
                                    <div class="form-group col-md-3">
                                        <label for="date-input1">Date d'entrée</label>
                                        <div class="input-group">
                                            <input type="text" name="dateEntree" class="form-control drgpicker" id="date-input1" value="<%= prevision.getDateEntee() %>" aria-describedby="button-addon2" required>
                                            <div class="input-group-append">
                                                <div class="input-group-text" id="button-addon-date"><span class="fe fe-calendar fe-16"></span></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label for="time-input2">Heure d'entrée</label>
                                        <div class="input-group">
                                            <input type="text" name="heureEntree" class="form-control" id="time-input2" placeholder="HH:MM:SS" value="<%= prevision.getHeureEntree() %>" aria-describedby="button-addon2" required>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-2 text-center">
                                        <label for="time-input2">Jusqu'a</label>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="date-input1">Date de sortie</label>
                                        <div class="input-group">
                                            <input type="text" name="dateSortie" class="form-control drgpicker" id="date-input1" value="<%= prevision.getDateSortie() %>" aria-describedby="button-addon2" required>
                                            <div class="input-group-append">
                                                <div class="input-group-text" id="button-addon-date"><span class="fe fe-calendar fe-16"></span></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label for="time-input2">Heure de sortie</label>
                                        <div class="input-group">
                                            <input type="text" name="heureSortie" class="form-control" id="time-input2" placeholder="HH:MM:SS" value="<%= prevision.getHeureSortie() %>" aria-describedby="button-addon2" required>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <p><h4 class="h4 mb-0 page-title">Duree d'escale : <%= prevision.getDureeHHmmSS() %></h4></p>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- .card -->
                    </div> <!-- .col-md-->
                    <!-- INFO SUPP -->
                    <div class="col-md-12">
                        <div class="card mb-4 shadow">
                            <div class="card-body my-n3">
                                <div class="row align-items-center">
                                    <div class="col">
                                        <p><h2 class="h2 mb-0 page-title">Informations supplémentaires</h2></p>                                   
                                        <p><h4 class="h4 mb-0 page-title">Durée de remorquage : <%= navire.getDureeRemorquage() %> minutes</h4></p>
                                    </div> <!-- .col -->
                                </div> <!-- .row -->
                            </div> <!-- .card-body -->
                        </div> <!-- .card -->
                    </div> <!-- .col-md-->
                    <!-- FIN INFO SUPP -->
                    <!-- FIN Prevision d'escale -->
                    <!-- Prestations -->
                    <div class="col-md-12">
                        <div class="card mb-4 shadow">
                            <div class="card-body">
                                <p><h2 class="h2 mb-0 page-title">Vos prestations</h2></p> 
                                <div class="form-row">
                                    <div class="col-md-12 mb-4">
                                        <div class="card shadow">
                                            <div class="card-body">
                                                <% for(int k = 0; k < lprestations.length; k++ ) {  %>
                                                    <p><%= lprestations[k].getDescription() %> <%= lprestations[k].getMontant().getAriary() %> Ar - <%= lprestations[k].getMontant().getEuro() %> Euro </p>
                                                <% } %>
                                            </div> <!-- / .card-body -->
                                        </div> <!-- / .card -->
                                    </div>
                                </div>
                            </div>
                        </div> <!-- .card -->
                    </div> <!-- .col-md-->
                    <div class="col-md-12">
                        <div class="card mb-4 shadow">
                            <div class="card-body">
                                <p><h2 class="h2 mb-0 page-title">Prestations</h2></p> 
                                <div class="form-row">
                                    <div class="col-md-12 mb-4">
                                        <div class="card shadow">
                                            <div class="card-body">
                                                <p class="mb-2"><strong>Ajoutez ou enlever des prestations</strong></p>
                                                <% 
                                                    int j = 0 ;
                                                    for(Prestation p : lp) { 
                                                    j++;
                                                    if(p.getIdPrestation() == 1 || p.getIdPrestation() == 4) {
                                                %>
                                                    <div>
                                                        <input type="checkbox" name="idPrestations" value="<%= p.getIdPrestation() %>" id="customCheck<%= j %>" checked="">
                                                        <label><%= p.getDescription() %></label>
                                                    </div>
                                                    <% } else { %>
                                                    <div>
                                                        <input type="checkbox" name="idPrestations" value="<%= p.getIdPrestation() %>" id="customCheck<%= j %>">
                                                        <label><%= p.getDescription() %></label>
                                                    </div>
                                                    <% } %>
                                                <% } %>
                                            </div> <!-- / .card-body -->
                                        </div> <!-- / .card -->
                                    </div>
                                </div>
                            </div>
                        </div> <!-- .card -->
                    </div> <!-- .col-md-->
                    <!-- FIN Prestations -->
                    <!-- QUAIS -->
                    <div class="col-md-12">
                        <div class="card mb-4 shadow">
                            <div class="card-body">
                                <p><h2 class="h2 mb-0 page-title">Quai numero <%= quaiPropose.getIdQuai() %></h2></p> 
                                <div class="row">
                                    <% for(int i = 0; i < lq.length; i++) { 
                                        if(lq[i].getIdQuai() == quaiPropose.getIdQuai()) {
                                    %>
                                    <div class="col-md-3">
                                        <div class="card shadow mb-4" style="background-color: greenyellow;">
                                            <div class="card-body text-center">
                                                <div class="card-text my-2">
                                                    <strong class="card-title my-0"><%= quaiPropose.getIdQuai() %></strong>
                                                </div>
                                            </div> <!-- ./card-text -->
                                        </div>
                                    </div> <!-- .col -->
                                    <% } else { %>
                                    <div class="col-md-3">
                                        <div class="card shadow mb-4">
                                            <div class="card-body text-center">
                                                <div class="card-text my-2">
                                                    <strong class="card-title my-0"><%= lq[i].getIdQuai() %></strong>
                                                </div>
                                            </div> <!-- ./card-text -->
                                        </div>
                                    </div> <!-- .col -->
                                    <% } %>
                                    <% } %>
                                </div> <!-- .row -->
                            </div>
                        </div>
                    </div> <!-- .col-md-->
                    <!-- FIN QUAIS -->
                    <!-- Ordre de priorite -->
                    <div class="col-md-12">
                        <div class="card mb-4 shadow">
                            <div class="card-body">
                                <p><h2 class="h2 mb-0 page-title">Ordre de priorite (le navire est surligne en vert)</h2></p> 
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
                                                    <% for(int i = 0; i < quaiPropose.getEscales().length; i++) { 
                                                        Escale e = quaiPropose.getEscales()[i];
                                                        if(e.getIdEscale() == 0) {
                                                    %>
                                                    <tr class="table-success">
                                                        <td><%= i+1 %></td>
                                                        <td><%= quaiPropose.getDescription() %></td>
                                                        <td><%= navire.getIdentifiant() %></td>
                                                        <td><%= navire.getDescription() %></td>
                                                        <td><%= navire.getClient().getDescription() %></td>
                                                        <td><%= e.getEntree() %></td>
                                                        <td><%= e.getSortie() %></td>
                                                        <td><%= e.getMontant().getAriary() %></td>
                                                        <td><%= e.getMontant().getEuro() %></td>
                                                    </tr>
                                                    <% } else  { %>
                                                    <tr>
                                                        <td><%= i+1 %></td>
                                                        <td><%= quaiPropose.getDescription() %></td>
                                                        <td><%= navire.getIdentifiant() %></td>
                                                        <td><%= navire.getDescription() %></td>
                                                        <td><%= navire.getClient().getDescription() %></td>
                                                        <td><%= e.getEntree() %></td>
                                                        <td><%= e.getSortie() %></td>
                                                        <td><%= e.getMontant().getAriary() %></td>
                                                        <td><%= e.getMontant().getEuro() %></td>
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
                    <input type="hidden" name="idNavire" value="<%= navire.getIdNavire() %>"
                    <div class="col-md-12">
                        <div class="mb-2" style="width: 50%; display: flex; flex-direction: row; justify-content: space-evenly; margin: 0 auto;">
                            <a href="prevision_choix_navire.jsp"><button type="button" class="btn mb-2 btn-outline-danger">annuler</button></a>
                            <button type="submit" class="btn mb-2 btn-outline-warning">modifier</button>
                            <a href="confirmePrevision"><button type="button" class="btn mb-2 btn-outline-success">confirmer</button></a>
                        </div>
                    </div>
                </form>
            </div>
            <!-- FIN CONTENT -->
        </div> <!-- .col-12 -->
    </div> <!-- .row -->
</div>
<%@ include file = "footer.html" %>