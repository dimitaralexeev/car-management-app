var vehicleIdToSend;
var expiredCostsArr;

// Choose the car id and add to variable - vehicleIdToSend
$('#chooseCar').change(function () {
  vehicleIdToSend = $('#chooseCar').val();
});

// Post request to add last refuel
$('#addConsumption').click(function () {
  $.ajax({
    url: "consumption/add",
    method: "POST",
    data: {
      quantity: $('#addQuantity').val(),
      pricePerLiter: $('#addPricePerLiter').val(),
      actualMileage: $('#addLastMileage').val(),
      vehicleId: vehicleIdToSend
    },
    success: function (response) {
      if (!response) {
        alert(response.status.text);
      } else {
        $('#consumptionModal').modal('hide');
      }
    },
    fail: function () {
      alert("Error!")
    }
  });
});

// Post request to add last cost
$('#addCost').click(function () {
  $.ajax({
    url: "cost/add",
    method: "POST",
    data: {
      typeOfCost: $('#chooseCostType').val(),
      price: $('#addPrice').val(),
      date: $('#addDate').val(),
      descprition: $('#addDescription').val(),
      validity: $('#chooseValidity').val(),
      vehicleId: vehicleIdToSend
    },
    success: function (response) {
      if (!response) {
        alert(response.status);
      } else {
        $('#costModal').modal('hide');
      }
    },
    fail: function () {
      alert("Error!")
    }
  });
});

// get consumptions data
function getConsumptions(btnId) {
  $.ajax({
    url: "/consumptions",
    method: "GET",
    data: { vehicleId: vehicleIdToSend }
  }).done(function (response) {
    
    postConsumptions(btnId, response);
  }).fail(function () {
    alert("No consumptions!")
  });
}

// post table of consumptions
function postConsumptions(btnId, response) {
  var totalPrice = 0;
  var totalDistance = 0;
  var totalQuantity = 0;
  var totalConsumption = 0;
  var counter = 0;

  for (i in response) {
    var tableRow = $('<tr><td>'+ compareToNull(response[i].quantity) +'</td> <td>'+ compareToNull(response[i].price/response[i].quantity) +'</td> <td>'
                    + compareToNull(response[i].distance) +'</td> <td>'+ compareToNull(response[i].avgConsumption).toFixed(1) 
                    +'</td> <td>'+ compareToNull(response[i].price).toFixed(2) 
                    +'</td>'+ addIconsToTablesRows(response[i].id) +'</tr>');
    
    totalPrice +=  Number(response[i].price);
    totalDistance += Number(response[i].distance);
    totalQuantity += Number(response[i].quantity);
    totalConsumption += Number(response[i].avgConsumption);
    counter ++;
    
    $('#consumptionItemsList').prepend(editAndClickBtnsAndDeleteOrEditRow(btnId, tableRow));   
    $('#consumptionTable').show();
  }

  $('#totalConsumptionPrice').text(totalPrice.toFixed(2));
  $('#totalAvgConsum').text((totalConsumption/counter).toFixed(1));
  $('#totalDistance').text(totalDistance);
  $('#totalQuantity').text(totalQuantity);
}

// get costs data
function getCosts(btnId) {
  $.ajax({
    url: "/costs",
    method: "GET",
    data: { vehicleId: vehicleIdToSend }
  }).done(function (response) {
    postCosts(btnId, response);
  }).fail(function () {
    alert("No costs!")
  });
}

// Post table of costs
function postCosts(btnId, response) {
  var totalPrice = 0;

  for (i in response) {
    var tableRow = $('<tr><td>'+ selectTypeOfCost(compareToNull(response[i].typeOfCost)) +'</td> <td>'
                    + compareToNull(response[i].price) +'</td> <td>'+ compareToNull(response[i].date) 
                    +'</td> <td>'+ compareToNull(response[i].descprition) 
                    +'</td>'+ addIconsToTablesRows(response[i].id) +'</tr>');

    totalPrice += Number(response[i].price);

    $('#costsItemsList').prepend(editAndClickBtnsAndDeleteOrEditRow(btnId, tableRow))
    $('#costsTable').show();
  }

  $('#totalCostsPrice').text(totalPrice.toFixed(2));
}

function getVehicle() {
  $.ajax({
    url: "vehicle/" + vehicleIdToSend,
    method: "GET"
  }).done(function (response) {
    if (response == null) { alert("Няма намерен автомобил"); return; }

    $('#carBrandTable').text(compareToNull(response.producer));
    $('#carModelTable').text(compareToNull(response.model));
    $('#licensePlateTable').text(compareToNull(response.licensePlate));

  }).fail(function () {
    alert("Something went wrong!");
  });
}

function selectTypeOfCost(typeOfCost) {
  var type;
  switch (typeOfCost) {
    case "repair":
      type = "Ремонт";
      break;
    case "insurance":
      type = "Застраховка";
      break;
    case "tax":
      type = "Данък";
      break;
    case "yearCheck":
      type = "Годишен преглед";
      break;
    case "vignette":
      type = "Пътна такса";
      break;
  }
  return type;
}

//Check if the user has expired costs
function getExpiredCosts(){
  $.ajax({
    method: "GET",
    url: "/expiredCosts"
  }).done(function (response) {
      if(response.status == 401) { alert(response.status.text); return; }
      if(response.length != 0){
        expiredCostsArr = response;
        
        $('#checkModalBtn').show();
      }
  }).fail(function () {
    alert("Oops, something went wrong!!!");
  });
}

//Post expired costs to modal
function postExpiredCosts(response){
  for(i in response){
    var html = $('#expiredCostsCloneListItems').clone();
    html.find('#expiredDate').text(compareToNull(response[i].date));
    html.find('#typeOfCost').text(selectTypeOfCost(compareToNull(response[i].typeOfCost)));
    html.find('#licensePlateNumber').text(compareToNull(response[i].vehicle.licensePlate));

    $('#expiredCostsList').append(html);
    html.show();
  }
}

//Parse string to html dom elements
function editAndClickBtnsAndDeleteOrEditRow(btnId, htmlString){

  htmlString.find('.remove').click(function () {
      if(btnId == "consmuptionTableBtn"){
        $.ajax({
          url: "/deleteConsumption",
          method: "DELETE",
          data: { 
            vehicleId: vehicleIdToSend,
            consumptionId: $(this).prop('id') 
          },
          complete: function (response) {
            if (response.status == 401) {
              alert(response.status.text);
              return;
            }
            if (response.status == 404) {
              alert(response.status.text);
              return;
            }

            alert("Success delete consumption!");
          },
          fail: function () {
            alert("Error");
          }
        });
      }else{
        $.ajax({
          url: "/deleteCost",
          method: "DELETE",
          data: { 
            vehicleId: vehicleIdToSend,
            costId: $(this).prop('id') 
          },
          complete: function (response) {
            if (response.status == 401) {
              alert(response.status.text);
              return;
            }
            if (response.status == 404) {
              alert(response.status.text);
              return;
            }
            
            alert("Success delete cost!");
          },
          fail: function () {
            alert("Error");
          }
        });
      }

      htmlString.remove();
    });

    // html.find('.edit').click(function () {
    //   if(btnId == "consmuptionTableBtn"){
    //     $('#consumptionModal').modal('show');
    //   }else{
    //     $('#costModal').modal('show');
    //   }
    // });

  return htmlString;
}

//add icons for edit and delete element to the table
function addIconsToTablesRows(id){
  var addElement = '<td> <a type="button" class="edit" id="'+ compareToNull(id) 
                    +'"><i class="fas fa-pen" style="color: rgb(131, 190, 63);"></i></a> <a type="button" class="remove" id="'+ compareToNull(id) 
                    +'"><i class="fa fa-trash" style="color: rgb(131, 190, 63);"></i></a> </td>';

  return addElement;
}