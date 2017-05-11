var anzBeacons = 0;
var freierOrt = true;
var ready = false; //TODO Remove this
$('#bildContainer').removeClass('beacon');

$(document).ready( function(){
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
  //Beacon auf Karte hinzufügen
  container.addEventListener("click", function (e) {
    //alert("Clicked");
    $('.mask').remove();
    $('.beaconContainer').click(function() {
      freierOrt = false;
    });
    if (freierOrt) {
      var x = e.clientX - container.getBoundingClientRect().left;
      x = Math.round(x);
      x = x - 10 ; //TODO an beacon größe anpassen
      x = x *(100/$('#bild').width());
      //alert(x);
      var y = e.clientY - container.getBoundingClientRect().top;
      y = Math.round(y);
      y = y - 10; //TODO an beacon größe anpassen
      y = y *(100/$('#bild').height());
      //alert(y);
      anzBeacons++;
      beaconHinzufuegen(x, y);
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
          'name': "NULL",
          'minor_id': minor_id,
          'pos_x': x,
          'pos_y': y,
          'description': "NULL",
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
    freierOrt = true;
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
      $('.listSpecialPlaces').addClass('closeMenu');
      $('.listSpecialPlaces').removeClass('open');
      setTimeout(function() {
        $('.listSpecialPlaces').css('display', 'none');
      }, 450);
      rotateArrow();
    }else {
      $('.listSpecialPlaces').css('display', 'block');
      $('.listSpecialPlaces').addClass('open');
      $('.listSpecialPlaces').removeClass('closeMenu');
      rotateArrow();
    }
  });

  $('.rdbBesondersJa').click(function() {
    $('.listSpecialPlaces').addClass('open');
    $('.listSpecialPlaces').removeClass('closeMenu');
  });

  $('.rdbBesondersNein').click(function() {
    $('.listSpecialPlaces').addClass('closeMenu');
    $('.listSpecialPlaces').removeClass('open');
  });

  $('.btnDelete').click(function() {
    deleteBeacon();
  })

//--------------Beacon Informationen zu entsprechendem Beacon hinzufuegen-------
/* container.addEventListener("click", function (e) {

    $('.beacon').click(function() {
      var beaconId = $('.actualBeacon').attr('class').split('-')[1];
      beaconId = beaconId.split(' ')[0];

      $.ajax({
        type: "POST",
        url: "FormFeld.inc.php",
        data: {'beaconId': beaconId}
      }).done(function() {
        $('body').css('background', 'green');
      }).fail(function() {
        $('body').css('background', 'red');
      });
    });
  });*/

});



//Drag n´Drop beaconBearbeiten

$(drag);
$(drop);
function drag() {
  $('.beaconBearbeiten').draggable({
    cursor: "move",
    revert: "invalid",
    opacity: 0.7,
    snap: "body",
    snapMode: "inner"
  });
}
function drop() {
  $('body').droppable({
    accept: ".beaconBearbeiten",
    hoverClass: 'hovered',
    drop: positioning
  });
}
function positioning(event, ui) {
  position = $(this).position();
  ui.draggable.animate({
    opacity: 1,
    top: position.top,
    left: position.left
  },200);
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
}

function showData() {
  var id = $('.actualBeacon').parent().attr('id').split('-')[1];
  console.log(id);
  var map_id = $('#bild').attr('alt');
  $.ajax({
    type: "POST",
    url: "EditorFiles/Formfeld.php",
    data: {
      'minor_id': id,
      'fk_map_id': map_id,
      'action': "show"
    }
  }).done(function() {
    $('.progress-bar').removeClass('active');
    $('.progress').css('filter', 'brightness(60%)');
    console.log("succesfull");
  }).fail(function() {
    $('.progress-bar').addClass('progress-bar-danger')
  });
}
//x = x *(100/$('#bild').width());
//$('.beacon-'+ anzBeacons).css('margin-top', y+'px');
