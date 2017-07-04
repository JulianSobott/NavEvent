$(document).ready(function() {
  $('.btnNeueKarteHinzufuegen').click(function() {
    window.location='index.php?action=guide';
  });
  $('.btnGetTheApp').click(function() {
    window.location='https://play.google.com/store/apps/details?id=com.navevent.navevent';
  });

  var actualPicture = 0; //index if the actual Picture. starts at 0
  bildershow();

  function bildershow() { //Function which changes the background image from the startheader
    var mapPictures = document.getElementsByClassName('karte');
    var number_pictures = mapPictures.length;

    if (actualPicture >= number_pictures) {
      actualPicture = 0;
    }
    var src_picture = $('.karte:eq('+actualPicture+')').attr('src');
    $('.startInfo').css('background-image', 'url('+src_picture+')');
    actualPicture++;
    setTimeout(bildershow, 8000);
  }

});
