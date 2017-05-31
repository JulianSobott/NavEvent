$('document').ready(function(){
  $('.btnPublish').hover(function() {
    $('.textPublishMap').addClass('in');
  });
  $('.publishMap').mouseleave(function() {
    $('.textPublishMap').removeClass('in');
  });
  $('.btnPublish').click(function() {
    $('.map_name').addClass('map_name_in');
    $('.mask').css('display', 'block');
    console.log("publish");
    //var map_id = $('#bild').attr('alt');
    //window.location = "http://localhost/NavEvent/php/pages/EditorFiles/qr_Code.php?map_id="+map_id;
  });
  $('#btnWeiter').click(function() {
    if ($('#tf_map_name').val() != "" ){
      var map_id = $('#bild').attr('alt');
      var map_name = $('#tf_map_name').val();
      console.log(map_id + " - " + map_name);
      $.ajax({
        type: "POST",
        url: "../includes/datenbank.inc.php",
        data: {
          'map_name': map_name,
          'map_id': map_id,
          'action': "map_name"
        }
      }).done(function() {
        window.location = "http://localhost/NavEvent/php/pages/EditorFiles/qr_Code.php?map_id="+map_id;
      }).fail(function() {
        $('#tf_map_name').css('border', '2px solid red');
      });
    }else {
      $('#tf_map_name').css('border', '2px solid red');
    }
  });
});
