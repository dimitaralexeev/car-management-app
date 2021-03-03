getUsername();

// Logout function for all pages
$('#logoutBtn').click(function(){
    $.ajax({
        url:"logout",
        method:"POST",
        complete:function(response){
            if(response.status == 401){
                alert("Unauthorized error")
            }

            openHtmlPage("index.html");
        }
    });
});

// Open html page function
function openHtmlPage(path){
    window.location.replace(path);
}

//Compare function for posting elemnts
function compareToNull(elementToCompare){
    if(elementToCompare === null || elementToCompare === "" || elementToCompare === []){return "unknown"}
    return elementToCompare;
}

// Get all vehicles from the database
function getVehicles(htmlPage){
    switch(htmlPage){
        case "home.html":
            $.ajax({
                method:"GET",
                url:"/vehicle/all",
                dataType:"JSON"
                }).done(function(response){
                    response.forEach(function(vehicle){
                        initVehicleList(vehicle);
                        
                    });
                }).fail(function(){
                    alert("Oops, something went wrong!!!");
                });
            break;
        case "vehicle.html":
            $.ajax({
                method:"GET",
                url:"/vehicle/all",
                dataType:"JSON"
                }).done(function(response){
                    response.forEach(function(vehicle){
                      
                        postVehicle(vehicle);
                    });
                }).fail(function(){
                    alert("Oops, something went wrong!!!");
                });
            break;
    }
  }

  function getUsername(){
      $.ajax({
        method:"GET",
        url:"/user"
      }).done(function(response){
        if(response.status == "401"){alert(response.status.text); return;}
        $('#userNameShow').text(response);
      }).fail(function(){
        
      });
  }




