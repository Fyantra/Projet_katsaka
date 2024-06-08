<%@ include file = "header.html" %>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-12">
            <div class="row align-items-center my-4">
                <div class="col">
                    <h2 class="h3 mb-0 page-title">Facturation</h2>
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
                                    <p><h4 class="h4 mb-0 page-title">Royal Navy - Petrolier </h4></p>
                                    <p><h6 class="h6 mb-0">national / international </h6></p>
                                    <p><h5 class="h5 mb-0 page-title">Profondeur : 5 metres</h5></p>
                                    <p><h6 class="h6 mb-0">Client : UK Navy - England</h6></p>
                                </div> <!-- .col -->
                            </div> <!-- .row -->
                        </div> <!-- .card-body -->
                    </div> <!-- .card -->
                </div> <!-- .col-md-->
                <!-- FIN A PROPOS -->
                <!-- Prevision d'escale -->
                <div class="col-md-12">
                    <div class="card mb-4 shadow">
                        <div class="card-body">
                            <p><h2 class="h2 mb-0 page-title">Escale</h2></p> 
                            <div class="form-row">
                                <div class="form-group col-md-2">
                                    <label for="date-input1">Date d'entrée</label>
                                    <div class="input-group">
                                        <p><h6 class="h6 mb-0">06/02/2023</h6></p>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="time-input2">Heure d'entrée</label>
                                    <div class="input-group">
                                        <p><h6 class="h6 mb-0">08:00:00</h6></p>
                                    </div>
                                </div>
                                <div class="form-group col-md-1 text-center">
                                    <label for="time-input2"></label>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="date-input1">Date de sortie</label>
                                    <div class="input-group">
                                        <div class="input-group">
                                            <p><h6 class="h6 mb-0">06/02/2023</h6></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="time-input2">Heure de sortie</label>
                                    <div class="input-group">
                                        <p><h6 class="h6 mb-0">10:00:00</h6></p>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <div class="card shadow mb-4" style="background-color: greenyellow;">
                                        <div class="card-body text-center">
                                            <div class="card-text my-2">
                                                <strong class="card-title my-0">Quai 1</strong>
                                            </div>
                                        </div> <!-- ./card-text -->
                                    </div>
                                </div>
                                <div class="form-group col-md-12">
                                    <p><h4 class="h4 mb-0 page-title">Duree d'escale : 2 h 00 min 00 s</h4></p>
                                </div>
                            </div>
                        </div>
                    </div> <!-- .card -->
                </div> <!-- .col-md-->
                <!-- FIN Prevision d'escale -->
                <div class="col-md-12">
                    <div class="mb-2" style="width: 50%; display: flex; flex-direction: row; justify-content: space-evenly; margin: 0 auto;">
                        <a href="prevision_choix_navire.jsp"><button type="button" class="btn mb-2 btn-outline-warning">Retour</button></a> 
                    </div>
                </div>
            </div>
            <!-- FIN CONTENT -->
        </div> <!-- .col-12 -->
    </div> <!-- .row -->
</div>
<%@ include file = "footer.html" %>