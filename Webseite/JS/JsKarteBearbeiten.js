var anzBeacons = ($('#bildContainer').children().length)-1;

var freierOrt = true;
$('#bildContainer').removeClass('beacon');
$(document).ready( function(){
  updateSidebar();
  var container = document.querySelector('#bildContainer');

  //Bild Container Grösse anpassen
  var widthBild = $('#bild').css('width');
  var heightBild = $('#bild').css('height');
  $('#bildContainer').css('width', widthBild);
  $('#bildContainer').css('height', heightBild);

  //Spinner bei Bild upload
  $('.btnUpload').click(function(){
    $('.spinner').addClass('animate');
  });

  //Remove mask when there are already Beacons on the map
  if(anzBeacons != 0){
    $('.mask_form').remove();
  }
  /*$('.beaconContainer').mouseEnter(function() {
    freierOrt = false;
    console.log(freierOrt);
  });
  $('.beaconContainer').Mouseleave(function() {
    freierOrt = false;
    console.log(freierOrt);
  });
*/
  //Beacon auf Karte hinzufügen
  container.addEventListener("click", function (e) {
    //alert("Clicked");
    $('.mask_form').remove();
    $('.beaconContainer').click(function() {
      freierOrt = false;
      console.log("not free");
    });
    if (freierOrt) {
      var x = e.clientX - container.getBoundingClientRect().left;
      x = Math.round(x);
      x = x - 5 ; //TODO an beacon größe anpassen
      x = x *(100/$('#bild').width());
      //alert(x);
      var y = e.clientY - container.getBoundingClientRect().top;
      y = Math.round(y);
      y = y - 5; //TODO an beacon größe anpassen
      y = y *(100/$('#bild').height());
      //alert(y);
      anzBeacons++;
      beaconHinzufuegen(x, y);
      addDefaultBeacon(x, y);
      collapsePlaces();
    }
    freierOrt = true;
    updateSidebar();
  });

  //-------Beacon clicked-------------
  container.addEventListener("click", function (e) {
    $('.beaconContainer').click(function() {
      $('.actualBeacon').removeClass('actualBeacon');
      $(this).children().addClass('actualBeacon');
      showData();
      freierOrt = false;
    });
  });
  //----------Beacon Bearbeiten----------
  $('.dropDown').click(function() {
    if ($('.listSpecialPlaces').hasClass('open')) {
      collapsePlaces();
    }else {
      openPlaces();
    }
  });

  $('.rdbBesondersJa').click(function() {
    $('.actualBeacon').addClass('specialBeacon');
    openPlaces();
  });

  $('.rdbBesondersNein').click(function() {
    $('.actualBeacon').removeClass('specialBeacon');
    collapsePlaces();
  });

  $('.btnDelete').click(function() {
    deleteBeacon();
  })
});



//Drag n´Drop beaconBearbeiten

$(drag);
$(drop);
function drag() {
  $('.beaconContainer').draggable({
    cursor: "move",
    revert: "invalid",
    opacity: 0.7,
    snap: "#bildContainer",
    snapMode: "inner"
  });
}
function drop() {
  $('#bildContainer').droppable({
    accept: ".beaconContainer",
    hoverClass: 'hovered',
    drop: positioning
  });
}
function positioning(event, ui) {
  position = $('.ui-draggable-dragging').position();
  beacon = $('.ui-draggable-dragging').attr('id').split('-')[1];
  console.log(beacon);
  x = position.left;
  x = x *(100/$('#bild').width()); //From px to %
  y = position.top;
  y = y *(100/$('#bild').height()); //From px to %
  console.log(x + " - " + y);
  ui.draggable.animate({
    opacity: 1,
    top: y+"%",
    left: x+"%"
  },200);
  updateBeaconPosition(beacon, x, y);
}

//-------------Funktionen--------------------
function beaconHinzufuegen (pX, pY){
  var beacon = document.createElement("span");
  var beaconContainer = document.createElement("div");
  document.getElementById("bildContainer").appendChild(beaconContainer);
  $('#bildContainer').children('div').addClass('beaconContainer');
  $('.beaconContainer').last().attr('id', 'beaconContainer-' + anzBeacons)
  document.getElementById("beaconContainer-"+anzBeacons).appendChild(beacon);
  $('#beaconContainer-'+ anzBeacons).children('span').addClass('beacon');
  $('.beacon').last().addClass(' beacon-' + anzBeacons);
  $('.actualBeacon').removeClass('actualBeacon');
  $('.beacon').last().addClass('actualBeacon');
  $('#beaconContainer-'+ anzBeacons).css('left', pX+'%');
  $('#beaconContainer-'+ anzBeacons).css('top', pY+'%');
  updateSidebar();
}

function addDefaultBeacon(x, y) {
  var minor_id = $('.beaconContainer').last().attr('id').split('-')[1];
  //alert(minor_id);
  var map_id = $('#bild').attr('alt');
  //alert(map_id);
  //----Beacon in DB anlegen (standard Werte)--------
  $.ajax({
    type: "POST",
    url: "../includes/datenbank.inc.php",
    data: {
      'id': "0",
      'name': "",
      'minor_id': minor_id,
      'pos_x': x,
      'pos_y': y,
      'description': "",
      'fk_map_id': map_id,
      'fk_special': "NULL",
      'fk_ordinary': "NULL",
      'action': "insert"
    }
  }).done(function() {
    $('.seite-rechts').css('filter', 'contrast(100%)');
  }).fail(function() {
    $('.seite-rechts').css('filter', 'contrast(10%)');
  });
}
function deleteBeacon(){
  $('.progress-bar').addClass('active');
  $('.progress').css('filter', 'brightness(100%)');
  var id = $('.actualBeacon').parent().attr('id').split('-')[1];
  var map_id = $('#bild').attr('alt');
  $.ajax({
    type: "POST",
    url: "../includes/datenbank.inc.php",
    data: {
      'minor_id': id,
      'map_id': map_id,
      'action': "delete"
    }
  }).done(function() {
    $('.progress-bar').removeClass('active');
    $('.progress').css('filter', 'brightness(60%)');
  }).fail(function() {
    $('.progress-bar').addClass('progress-bar-danger')
  });
  $('.actualBeacon').remove();
  updateSidebar();
}

function rotateArrow() {
  $('.dropDown').toggleClass('rotateArrow');
}

function saveData(field, value) {
  $('.progress-bar').addClass('active');
  $('.progress').css('filter', 'brightness(100%)');
  var id = $('.actualBeacon').parent().attr('id').split('-')[1];
  var map_id = $('#bild').attr('alt');
  var position = $('.actualBeacon').parent().attr('style');
  console.log(position);

  if(field == "rdbSpecialPlace"){
    field = "fk_special";
    if(value == "Toiletten")
      value = 1;
    else if(value == "Cafeteria")
      value = 2;
    else if(value == "Notausgang")
      value = 3;
    else if(value == "Infopoint")
      value = 4;
    else
      value = 5;
  }
  if(field == "rdbBesonders" && value =="kein_Besonderer_Ort"){
    field = "fk_special";
    value = "NULL";
  }

  $.ajax({
    type: "POST",
    url: "../includes/datenbank.inc.php",
    data: {
      'minor_id': id,
      'map_id': map_id,
      'field': field,
      'value': value,
      'position': position,
      'action': "update"
    }
  }).done(function() {
    $('.progress-bar').removeClass('active');
    $('.progress').css('filter', 'brightness(60%)');
  }).fail(function() {
    $('.progress-bar').addClass('progress-bar-danger')
  });
  updateSidebar();
}
function updateBeaconPosition(beacon, x, y) {
  var id = beacon;
  var map_id = $('#bild').attr('alt');
  $.ajax({
    type: "POST",
    url: "../includes/datenbank.inc.php",
    data: {
      'minor_id': id,
      'fk_map_id': map_id,
      'pos_x': x,
      'pos_y': y,
      'action': "updateBeaconPosition"
    }
  });
}
function showData() {
  var id = $('.actualBeacon').parent().attr('id').split('-')[1];
  console.log(id);
  var map_id = $('#bild').attr('alt');
  $.ajax({
    type: "POST",
    url: "../includes/datenbank.inc.php",
    data: {
      'minor_id': id,
      'fk_map_id': map_id,
      'action': "show"
    }
  }).success(function(data) {
    var data_obj = JSON.parse(data);
    $('#tfName').val(data_obj['name']);
    $('#tfDescription').val(data_obj['description']);
    if (data_obj['fk_special'] != "0" || data_obj['fk_ordinary'] != "0" ) {
      $('.rdbBesondersJa').prop("checked", true);
      openPlaces();
    }else {
      $('.rdbBesondersNein').prop("checked", true);
      collapsePlaces();
    }
    console.log(data_obj);
    $('.progress-bar').removeClass('active');
    $('.progress').css('filter', 'brightness(60%)');
    console.log("succesfull");
  }).fail(function() {
    $('.progress-bar').addClass('progress-bar-danger')
  });
}

function updateSidebar() {
  var map_id = $('#bild').attr('alt');
  //$('.seitenmenue').css('background', 'blue');
  console.log("updateSidebar");
  $.ajax({
    type: "POST",
    url: "../includes/datenbank.inc.php",
    data: {
      'map_id': map_id,
      'action': "updateSidebar",
    }
  }).success(function(data) {
    $('.seitenmenue').html(data);
  }).fail(function() {
    $('.progress-bar').addClass('progress-bar-danger')
  });
}

function collapsePlaces() {
  $('.rdbBesondersNein').prop("checked", true);
  if ($('.listSpecialPlaces').hasClass('open')) {
    $('.listSpecialPlaces').addClass('closeMenu');
    $('.listSpecialPlaces').removeClass('open');
    setTimeout(function() {
      $('.listSpecialPlaces').css('display', 'none');
    }, 450);
    rotateArrow();
  }
}

function openPlaces() {
  $('.rdbBesondersJa').prop("checked", true);
  $('.listSpecialPlaces').css('display', 'block');
  $('.listSpecialPlaces').addClass('open');
  $('.listSpecialPlaces').removeClass('closeMenu');
  rotateArrow();
}
//x = x *(100/$('#bild').width());
//$('.beacon-'+ anzBeacons).css('margin-top', y+'px');
