$(document).ready(function(){

  $('.btnUpload').click(function(){
    $('.spinner').addClass('animate');
  });

//---------rechtspfeil wird geklickt-------------
  $('.rechtspfeil').click(function()
  {
    if($('.slide0').hasClass('is-active'))
    {
      $('.hilfe00').removeClass('hilfeAnimate-in');
      $('.hilfe01').removeClass('hilfeAnimate-out');
      $('.hilfe00').addClass('hilfeAnimate-out');
      $('.hilfe01').addClass('hilfeAnimate-in');
    }else if ($('.slide2').hasClass('is-active')) {
      $('.hilfe00').removeClass('hilfeAnimate-out');
      $('.hilfe01').removeClass('hilfeAnimate-in');
      $('.hilfe01').addClass('hilfeAnimate-out');
      $('.hilfe02').addClass('hilfeAnimate-in');
    }
  });

  //---------linkspfeil wird geklickt-------------
  $('.linkspfeil').click(function()
  {
    if($('.slide2').hasClass('is-active'))
    {
      $('.hilfe02').removeClass('hilfeAnimate-in');
      $('.hilfe03').removeClass('hilfeAnimate-out');
      $('.hilfe01').addClass('hilfeAnimate-in');
      $('.hilfe02').addClass('hilfeAnimate-out');
    }else if ($('.slide3').hasClass('is-active')) {
      $('.hilfe02').removeClass('hilfeAnimate-out');
      $('.hilfe03').removeClass('hilfeAnimate-in');
      $('.hilfe02').addClass('hilfeAnimate-in');
      $('.hilfe03').addClass('hilfeAnimate-out');
    }
  });

});
