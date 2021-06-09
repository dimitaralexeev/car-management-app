var vehicleIdToSend;
var chosenTable;
var expiredCostsArr;

// Choose the car and type of reference from the car and references lists and add to variables - vehicleIdToSend, chosenTable
$('#chooseCar, #chooseTable').change(function () {
  vehicleIdToSend = $('#chooseCar').val();
  chosenTable = $('#chooseTable').val();
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
        alert(response.status.text);
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
function getConsumptions() {
  $.ajax({
    url: "/consumptions",
    method: "GET",
    data: { vehicleId: vehicleIdToSend }
  }).done(function (response) {
    postConsumptions(response);
  }).fail(function () {
    alert("No consumptions!")
  });
}

// post table of consumptions
function postConsumptions(response) {
  for (i in response) {
    var tableRow = '<tr><td>'+ compareToNull(response[i].quantity) +'</td> <td>'+ compareToNull(response[i].price/response[i].quantity) +'</td> <td>'
                    + compareToNull(response[i].distance) +'</td> <td>'+ compareToNull(response[i].avgConsumption) 
                    +'</td> <td>'+ compareToNull(response[i].price) +'</td> </tr>';

    $('#consumptionItemsList').prepend(tableRow);
    $('#consumptionTable').show();
  }
}

// get costs data
function getCosts() {
  $.ajax({
    url: "/costs",
    method: "GET",
    data: { vehicleId: vehicleIdToSend }
  }).done(function (response) {
    postCosts(response);
  }).fail(function () {
    alert("No costs!")
  });
}

// Post table of costs
function postCosts(response) {
  for (i in response) {
    var tableRow = '<tr><td>'+ selectTypeOfCost(compareToNull(response[i].typeOfCost)) +'</td> <td>'
                    + compareToNull(response[i].price) +'</td> <td>'+ compareToNull(response[i].date) 
                    +'</td> <td>'+ compareToNull(response[i].descprition) +'</td> </tr>';

    $('#costsItemsList').prepend(tableRow)
    $('#costsTable').show();
  }
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
      if(response.status == "401") { alert(response.status.text); return; }
      if(response.length != 0){
        expiredCostsArr = response;
        console.log(expiredCostsArr);
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