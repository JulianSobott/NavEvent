$(document).ready(function() {
  $('.btnNeueKarteHinzufuegen').click(function() {
    window.location='template.php?action=guide';
  });

  var slideIndex = 0;
  bildershow();

  function bildershow() {
    var i;
    var x = document.getElementsByClassName('karte');
    for(var i=1;i<=x.length;i++)
    {
    	$('.karte:nth-child(' + i + ')').css('display', 'none');
    }
    slideIndex++;
    if(slideIndex > x.length){slideIndex = 1}
    $('.animate-in').addClass('animate-out');
    $('.animate-in').removeClass('animate-in');
    $('.karte:nth-child(' + slideIndex + ')').removeClass('animate-out');
    $('.karte:nth-child(' + slideIndex + ')').addClass('animate-in');
    setTimeout(bildershow, 6000);
  }

});
