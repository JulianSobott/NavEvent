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
    $('.beacon').click(function() {
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


      //----Beacon in DB anlegen (standard Werte)--------
      $.ajax({
        type: "POST",
        url: $('#daten').attr('action'),
        data: {
          'beaconId': 0,
          'name': "NULL",
          'besonders': false,
          'besondersName': "NULL",
          'informationen': "NULL",
          'posX': x,
          'posY': y
        }
      }).done(function() {
        $('.sumbit').css('background', '#122');
      }).fail(function() {
        $('.sumbit').css('background', '#f00');
      });
    }
    freierOrt = true;
  });
  container.addEventListener("click", function (e) {

    $('.beacon').click(function() {
      $('.actualBeacon').removeClass('actualBeacon')
      $(this).addClass('actualBeacon');
      freierOrt = false;
    });
    $('.btnDelete').click(function() {
      $('.actualBeacon').remove();
    })
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

  $('.deleteBeacon').click(function() {
    $('.actualBeacon').remove();
    closeWindow();
  })

//--------------Beacon Informationen zu entsprechendem Beacon hinzufuegen-------
  container.addEventListener("click", function (e) {

    $('.beacon').click(function() {
      var beaconId = $('.actualBeacon').attr('class').split('-')[1];
      beaconId = beaconId.split(' ')[0];

      if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
        //alert("Step 1");
      }else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
      }
      xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
          document.getElementById("databaseStuff").innerHTML = this.responseText;
        }
      };
      xmlhttp.open("GET","http://localhost/NavEvent/php/includes/FormFeld.inc.php?beaconId="+beaconId, true);
      xmlhttp.send();


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
  });

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
  $('#beaconContainer-'+ anzBeacons).css('left', pX+'%');
  $('#beaconContainer-'+ anzBeacons).css('top', pY+'%');
}

function rotateArrow() {
  $('.dropDown').toggleClass('rotateArrow');
}

//x = x *(100/$('#bild').width());
//$('.beacon-'+ anzBeacons).css('margin-top', y+'px');
