var anzBeacons = ($('#bildContainer').children().length)-1; //-1 Because the Container has always the img as child
var freierOrt = true;

$(document).ready( function(){
  updateSidebar();
  var container = document.querySelector('#bildContainer');
  //Default Settings for the BildContainer
  widthBild = $('#bild').css('width');
  heightBild = $('#bild').css('height');
  $('#bildContainer').css('width', widthBild);
  $('#bildContainer').css('height', heightBild);
  //Adjust bildContainer and thereby the beacon position depending on the Window width
  $( window ).resize(function() {
    widthBild = $('.mitte').css('width');
    heightBild = $('#bild').css('height');
    $('#bildContainer').css('width', widthBild);
    $('#bildContainer').css('height', heightBild);
  });
  //Spinner bei Bild upload
  $('.btnUpload').click(function(){
    $('.spinner').addClass('animate');
  });

  //Remove mask when there are already Beacons on the map
  if(anzBeacons != 0){
    $('.mask_form').remove();
  }

  //Beacon hovered
  $(document).on('mouseenter', '.beaconContainer', function() {
    id = this.id.split('-')[1];
    show_biDescription(id);
  })

  $(document).on('mouseleave', '.beaconContainer', function() {
    id = this.id.split('-')[1];
    undo_show_biDescription(id);
  })


//-------Beacon clicked-------------
  $('.kartenContainer').on('click', '.beaconContainer', function(e) {
    $('.actualBeacon').removeClass('actualBeacon');
    $(this).children().addClass('actualBeacon');
    $('.seite-rechts').css('filter', 'contrast(100%)');
    showData();
    freierOrt = false;
  });

  //Beacon auf Karte hinzufügen
  $('.kartenContainer').on('click', function(e) {
    $('.mask_form').remove();
    $('.beaconContainer').click(function() {
      freierOrt = false;
    });
    if (freierOrt) {
      var x = e.clientX - container.getBoundingClientRect().left;
      x = Math.round(x);
      x = x - 8;
      x = x *(100/$('#bild').width());
      var y = e.clientY - container.getBoundingClientRect().top;
      y = Math.round(y);
      y = y - 8;
      y = y *(100/$('#bild').height());
      anzBeacons++;
      beaconHinzufuegen(x, y);
      addDefaultBeacon(x, y);
      collapsePlaces();
    }
    freierOrt = true;
    updateSidebar();
    $(drag);
    $(drop);
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



//Drag n´Drop Beacon
$(drag);
$(drop);
function drag() {
  $('.beaconContainer').draggable({
    cursor: "move",
    revert: "invalid",
    opacity: 0.7,
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
  position = $('.ui-draggable-dragging').position(); //Element which is currently dragging
  beacon = $('.ui-draggable-dragging').attr('id').split('-')[1]; //beacon id number
  x = position.left;
  x = x *(100/$('#bild').width()); //From px to %
  y = position.top;
  y = y *(100/$('#bild').height()); //From px to %
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

function updateBeaconPosition(beacon, x, y) { //Updates Beacon Position in DB if its changed through Drag and Drop
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
function showData() { //Function for the right Form to show the Values for the actual Beacon
  var id = $('.actualBeacon').parent().attr('id').split('-')[1];
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
    var data_obj = JSON.parse(data); //TODO Sometimes get this SyntaxError: JSON.parse: unexpected non-whitespace character after JSON data at line 1 column 153 of the JSON data
//-------Sets the values of all fields according to the Values from the DB-----
    $('#tfName').val(data_obj['name']);
    $('#tfDescription').val(data_obj['description']);
    if (data_obj['fk_special'] != "0" || data_obj['fk_ordinary'] != "0" ) {
      $('.rdbBesondersJa').prop("checked", true);
      openPlaces();
    }else {
      $('.rdbBesondersNein').prop("checked", true);
      collapsePlaces();
    }
    $('.progress-bar').removeClass('active');
    $('.progress').css('filter', 'brightness(60%)');
  }).fail(function() {
    $('.progress-bar').addClass('progress-bar-danger')
  });
}

function updateSidebar() { //GET the new Values for the left sidebar from the DB
  var map_id = $('#bild').attr('alt');
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

function collapsePlaces() { //Function for the right Form to collapse the places list
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

function openPlaces() { //Function for the right Form to open the places list
  $('.rdbBesondersJa').prop("checked", true);
  $('.listSpecialPlaces').css('display', 'block');
  $('.listSpecialPlaces').addClass('open');
  $('.listSpecialPlaces').removeClass('closeMenu');
  rotateArrow();
}

function show_biDescription(id) { //Highlights the beaconInfoContainer Which fits to the hovered Beacon
  $('.beaconInfoContainer-' + id).addClass('show');
  $('.beaconInfoContainer-' + id).removeClass('undo_show');
  var top =  -parseInt($('.seitenmenue').children('div:nth-child(1)').css('height')); //Prevevent, that the first element is scrolled over
  for (var i = 1; i < id; i++) {  //Get the height of all Elements above the shown Element
    height = parseInt($('.seitenmenue').children('div:nth-child('+i+')').css('height'));
    top += height;
  }
  $('.seitenmenue').stop().animate({
  scrollTop: top
  }, 800);
}

function undo_show_biDescription(id) {  //Undo the Highlighting, added funtion before
  $('.beaconInfoContainer-' + id).removeClass('show');
  $('.beaconInfoContainer-' + id).addClass('undo_show');
}
