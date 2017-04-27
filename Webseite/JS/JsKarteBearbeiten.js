var anzBeacons = 0;
var freierOrt = true;
var ready = false; //TODO Remove this
$('#bildContainer').removeClass('beacon');
$(document).ready( function(){


  var container = document.querySelector('#bildContainer');

  //Bild Container Grösse anpassen
  var widthBild = $('.bild').css('width');
  var heightBild = $('.bild').css('height');
  $('#bildContainer').css('width', widthBild);
  $('#bildContainer').css('height', heightBild);

  //Beacon auf Karte hinzufügen
  container.addEventListener("click", function (e) {

    $('.beacon').click(function() {
      freierOrt = false;
    });
    $('.beaconBearbeiten').click(function() {
      freierOrt = false;
    })
    if (freierOrt) {
      //beaconBearbeiten Fenster ausblenden
      closeWindow();
      var x = e.clientX - container.getBoundingClientRect().left;
      x = Math.round(x);
      x = x - 10 ; //TODO an beacon größe anpassen
      var y = e.clientY - container.getBoundingClientRect().top;
      y = Math.round(y);
      y = y - 10; //TODO an beacon größe anpassen
      anzBeacons++;
      var beacon = document.createElement("span");
      document.getElementById("bildContainer").appendChild(beacon);
      $('#bildContainer').children('span').addClass('beacon');
      $('.beacon:last-child').addClass(' beacon-' + anzBeacons);
      $('.beacon-'+ anzBeacons).css('margin-left', x );
      $('.beacon-'+ anzBeacons).css('margin-top', y);

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
      $('.beaconBearbeiten').css('margin-left', $(this).css('margin-left'));
      var margin_top = $(this).css('margin-top');
      margin_top= parseInt(margin_top)-310;
      margin_top = margin_top+"px";
      $('.beaconBearbeiten').css('margin-top', margin_top);
      openWindow();
      freierOrt = false;
    });
    $('.beaconBearbeiten').click(function() {
      freierOrt = false;
    })
    $('.btnDelete').click(function() {
      $('.actualBeacon').remove();
      $('.beaconBearbeiten').removeClass('fensterFadeIn');
      $('.beaconBearbeiten').addClass('fensterFadeOut');
    })
  });

  //----------Beacon Bearbeiten----------
  $('.close').click(function() {
    closeWindow();
  });

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

  $('.expand').click(function() {
    $('.tfInfos').toggleClass('tfExpand');
    if ($('.tfInfos').hasClass('tfExpand')) {
      $(this).html('Weniger..');
    }else {
      $(this).html('Mehr..');
    }
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
        //alert("step 2");
        if (this.readyState == 4 && this.status == 200) {
          document.getElementById("databaseStuff").innerHTML = this.responseText;
          //alert("step 3");
        }
      };
      xmlhttp.open("GET","FormFeld.inc.php?beaconId="+beaconId, true);
      xmlhttp.send();
      //alert("finished");
      /*
      $.ajax({
        type: "POST",
        url: "FormFeld.inc.php",
        data: {'beaconId': beaconId}
      }).done(function() {
        $('body').css('background', 'green');
      }).fail(function() {
        $('body').css('background', 'red');
      });*/
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
function closeWindow() {
  $('.beaconBearbeiten').removeClass('fensterFadeIn');
  $('.beaconBearbeiten').addClass('fensterFadeOut');
  $('.listSpecialPlaces').removeClass('open');
  $('.listSpecialPlaces').addClass('closeMenu');
  $('.listSpecialPlaces').css('display', 'none');
  //$('.rdbBesondersNein').prop("checked", true);
  $('.dropDown').removeClass('rotateArrow');
  $('.tfInfos').removeClass('tfExpand');
  $('.expand').html('Mehr..');
}

function openWindow() {
  $('.listSpecialPlaces').removeClass('closeMenu');
  $('.beaconBearbeiten').removeClass('fensterFadeOut');
  $('.beaconBearbeiten').addClass('fensterFadeIn');
  $('.listSpecialPlaces').css('display', '');
}

function rotateArrow() {
  $('.dropDown').toggleClass('rotateArrow');
}
