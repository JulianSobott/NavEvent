$( document ).ready(function() {

  var anzSlides=3;
  var animationTime= 1200;

  //-------erschaffen des Counters---------
  for(var i=0;i<anzSlides;i++)
  {
  	var counter=document.createElement("span");
  	document.getElementById("counter").appendChild(counter);
  	$('#counter').children().addClass("dot");
  }

  for(var i=1;i<=anzSlides;i++)
  {
  	$('.dot:nth-child(' + i + ')').addClass("dot"+i);
  }

  //--------erschaffen der Slides-----------
/*
  for(var i=0;i<anzSlides;i++)
  {
  	var slides=document.createElement("DIV");
  	document.getElementById("slides").appendChild(slides);
  	$('#slides').children().addClass("slide");
  }

  for(var i=1;i<=anzSlides;i++)
  {
  	$('.slide:nth-child(' + i + ')').addClass("slide"+i);
  }
*/
  $('.slide:nth-child(' + 1 + ')').addClass("is-active");
  $('.dot:nth-child(' + 1 + ')').addClass("actual");



  //-----rechts Pfeil----------
  var viable=true;
  var rechts=true;
  var links=false;
  if(links==false)
  {
  	$('.linkspfeil').css('display', 'none');
  }
  $('.rechtspfeil').click(function()
  {
    if ($('.slide:last-child()').hasClass('is-active')||viable==false)
    {
  		rechts=false;
  		alert("rechtsfalse");
    }
    else
    {
  		viable=false;
  		links=true;
  		if($('.slide:last-child()').prev().hasClass('is-active'))
  		{
  			rechts=false;
  		}
  		else
  		{
  			rechts=true;
  		}
  		var nextdot=$('.actual').next();
      $('.actual').removeClass('actual');
      nextdot.addClass('actual');
      $('.is-active').addClass('right-out');
      $('.is-active').next().addClass('right-in');
      $('.right-in').addClass('is-active');

  		//rechtsPfeil ausblenden
  		if(rechts==false)
  		{
  			setTimeout(function()
  			{
  				$('.rechtspfeil').removeClass('fadeIn');
  				$('.rechtspfeil').addClass('fadeOut');
  			},animationTime-200);
  		}

  		//linkspfeil einblenden
  		if(links==true)
  		{
  			setTimeout(function()
  			{
  				$('.linkspfeil').addClass('fadeIn');
  				$('.linkspfeil').removeClass('fadeOut');
  			},animationTime-200);
  		}

      setTimeout(function()
      {

  			$('.right-out').removeClass('is-active');
        $('.is-active').removeClass('right-in');
        $('.is-active').prev().removeClass('right-out');
  			$('.material-icons').css('display', 'inline-block');
  			viable=true;
      }, animationTime);

    }

  });

  //-------links pfeil------------
  $('.linkspfeil').click(function()
  {
    if ($('.slide:first-child()').hasClass('is-active')||viable==false)
    {

    }
    else
    {
  		viable=false;
  		rechts=true;
  		if($('.slide:first-child()').next().hasClass('is-active'))
  		{
  			links=false;
  		}else {
  			links=true;
  		}
      var nextdot=$('.actual').prev();
      $('.actual').removeClass('actual');
      nextdot.addClass('actual');
      $('.is-active').addClass('left-out');
      $('.is-active').prev().addClass('left-in');
      $('.left-in').addClass('is-active');

  		if(rechts==true)
  		{
  			setTimeout(function()
  			{
  				$('.rechtspfeil').addClass('fadeIn');
  				$('.rechtspfeil').removeClass('fadeOut');
  			}, animationTime-200);
  		}
  		if(links==false)
  		{
  			setTimeout(function()
  			{
  				$('.linkspfeil').addClass('fadeOut');
  				$('.linkspfeil').removeClass('fadeIn');
  			}, animationTime-200);
  		}

      setTimeout(function()
      {
        $('.left-out').removeClass('is-active');
        $('.is-active').removeClass('left-in');
        $('.is-active').next().removeClass('left-out');
  			viable=true;
      }, animationTime);
    }
  });

  //-----------dot clicked----------
  $('.dot').click(function()
  {
  	var dotBefore=false;
  	var dotAfter=false;
  	$('.linkspfeil').css('display', 'inline-block');
  	$('.linkspfeil').addClass('fadeIn');



  	if($(this).prevAll().hasClass('actual'))
  	{
  		dotBefore=true;
  	}else if ($(this).nextAll().hasClass('actual')) {
  		dotAfter=true;
  	}
  	$('.dot.actual').removeClass('actual');
  	$(this).addClass('actual');
  	for(var i=1;i<=anzSlides;i++)
  	{
  		if($('.dot'+i).hasClass('actual'))
  		{
  			$('.slide'+i).addClass('is-active')

  			if(dotAfter==true)
  			{
  				$('.slide.left-in').removeClass('left-in');
  				$('.slide.right-in').removeClass('right-in');
  				$('.slide'+i).addClass('left-in');

  			}else if (dotBefore==true)
  			{
  				$('.slide.right-in').removeClass('right-in');
  				$('.slide.left-in').removeClass('left-in');
  				$('.slide'+i).addClass('right-in');
  			}

  			for(var h=1;h<=anzSlides;h++)
  			{
  				if(h!=i)
  				{
  					$('.slide'+h).removeClass('is-active');
  				}
  			}
  		}
  	}
  	if($('.slide:last').hasClass('is-active'))
  	{
  		$('.rechtspfeil').removeClass('fadeIn');
  		$('.rechtspfeil').addClass('fadeOut');
  	}else {
  		$('.rechtspfeil').addClass('fadeIn');
  		$('.rechtspfeil').removeClass('fadeOut');
  	}
  	if($('.slide:first').hasClass('is-active'))
  	{
  		$('.linkspfeil').removeClass('fadeIn');
  		$('.linkspfeil').addClass('fadeOut');
  	}else {
  		$('.linkspfeil').addClass('fadeIn');
  		$('.linkspfeil').removeClass('fadeOut');
  	}
  	setTimeout(function(){
  		$('.right-in').removeClass('right-in');
  		$('.left-in').removeClass('left-in');
  	}, animationTime);
  });

});
