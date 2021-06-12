getUsername();
addHeaderToAllPages();

// Logout function for all pages
$('#logoutBtn').click(function () {
    $.ajax({
        url: "logout",
        method: "POST",
        complete: function (response) {
            if (response.status == 401) {
                alert("Unauthorized error")
            }
            
            openHtmlPage("index.html");
        }
    });
});

// Open html page function
function openHtmlPage(path) {
    window.location.replace(path);
}

//Compare function for posting elemnts
function compareToNull(elementToCompare) {
    if (elementToCompare === null || elementToCompare === "" || elementToCompare === []) { return "unknown" }
    return elementToCompare;
}

// Get all vehicles from the database
function getVehicles(htmlPage) {
    switch (htmlPage) {
        case "home.html":
            $.ajax({
                method: "GET",
                url: "/vehicle/all",
                dataType: "JSON"
            }).done(function (response) {
                response.forEach(function (vehicle) {
                    initVehicleList(vehicle);
                });
            }).fail(function () {
                alert("Oops, something went wrong!!!");
            });
            break;
        case "vehicle.html":
            $.ajax({
                method: "GET",
                url: "/vehicle/all",
                dataType: "JSON"
            }).done(function (response) {
                response.forEach(function (vehicle) {
                    postVehicle(vehicle);
                });
            }).fail(function () {
                alert("Oops, something went wrong!!!");
            });
            break;
    }
}

//Get username and set it to the nameTag
function getUsername() {
    $.ajax({
        method: "GET",
        url: "/user"
    }).done(function (response) {
        if (response.status == "401") { alert(response.status.text); return; }
        $('#userNameShow').text(response.username);
    }).fail(function () {

    });
}

//Add header to a page
function addHeaderToAllPages(){

    var htmlHeader = $('<div class="container">'
                    +'  <nav class="navbar navbar-expand-lg navbar-light bg-light justify-content-md-between pt-1 pb-1 px-0">'
                    +'    <i class="fas fa-car-side mr-1" style="font-size: 100px; color: rgb(104, 136, 75);"></i>'
                    +'    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"'
                    +'      aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">'
                    +'      <span class="navbar-toggler-icon"></span>'
                    +'    </button>'
                    +'    <div class="collapse navbar-collapse flex-grow-0" id="navbarSupportedContent">'
                    +'      <ul class="navbar-nav mr-auto">'
                    +'        <li class="nav-item">'
                    +'          <a class="nav-link btn" href="home.html" id="mainPage">Главна страница</a>'
                    +'        </li>'
                    +'        <li class="nav-item">'
                    +'          <a class="nav-link btn" href="vehicle.html" id="carPage">Вашите автомобили</a>'
                    +'        </li>'
                    +'        <li class="nav-item">'
                    +'          <a class="nav-link btn" id="logoutBtn" style="cursor: pointer;">Изход</a>'
                    +'        </li>'
                    +'        <li class="nav-item">'
                    +'          <a class="nav-link btn" href="profil.html" id="userNameShow">username</a>'
                    +'        </li>'
                    +'      </ul>'
                    +'    </div>'
                    +'  </nav>'
                    +'</div>');

    $('#header').append(htmlHeader);
}





