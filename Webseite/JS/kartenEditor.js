$('document').ready(function(){
  $('.btnPublish').hover(function() {
    $('.textPublishMap').addClass('in');
  });
  $('.publishMap').mouseleave(function() {
    $('.textPublishMap').removeClass('in');
  });
  $('.btnPublish').click(function() {
    var map_id = $('#bild').attr('alt');
    window.location = "http://localhost/NavEvent/php/pages/EditorFiles/qr_Code.php?map_id="+map_id;
  });
  
});
