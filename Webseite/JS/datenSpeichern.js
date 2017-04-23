//----------Beacons Bearbeiten--------------------
$(document).ready(function(){
  $('#daten').submit(function(event) {
    event.preventDefault();
    //TODO KartenId und beaconId
    var beaconId = $('.actualBeacon').attr('class').split('-')[1];
    var name = $('.tfName').val();
    var besonders = false;
    var besondersName = "";
    if($('.rdbBesondersJa:checked').val() == "Besonderer_Ort")
    {
      besonders = 1;
      besondersName = $("input[name = 'rdbSpecialPlace']:checked").val();
      if(besondersName == "sonstiges")
      {
        besondersName = $('.tfSonstiges').val();
      }
    }
    var informationen = $('.tfInfos').val();
    alert(informationen);
    var posX = $('.actualBeacon').css('margin-left');
    var posY = $('.actualBeacon').css('margin-top');
    $.ajax({
      type: "POST",
      url: $('#daten').attr('action'),
      data: {
        'beaconId': beaconId,
        'name': name,
        'besonders': besonders,
        'besondersName': besondersName,
        'informationen': informationen,
        'posX': posX,
        'posY': posY
      }
    }).done(function() {
      $('.submit').css('background', '#122');
    }).fail(function() {
      $('.submit').css('background', '#f00');
    });
    return false;
  });

  $('#fKartenKonfiguration').submit(function(event)
  {
    event.preventDefault();
    var kartenName = $('.kartenName').val();
    $.ajax({
      type: "POST",
      url: $('#daten').attr('action'),
      data: {
        'kartenName': kartenName,
      }
    });
    return false
  });
});
