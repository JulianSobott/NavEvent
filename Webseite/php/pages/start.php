<div class="container-fluid startInfo">
  <div class="row">
    <div class="bildershow">
      <?php
      $folder = opendir("Bilder/Bildershow/");
      $indexPicture = 1;
      while ($picture = readdir($folder)){
        if($picture !== "." && $picture !== ".."){?>
        <img class="bild karte karte-<?php echo $indexPicture;?> img-responsive" src="Bilder/Bildershow/<?php echo $picture;?>" alt="Beispiel Karte">
        <?php
        $indexPicture++;
        }
      }
      ?>
    </div>
    <div class="col-md-4">
      <img class="bild logo logo_start img-responsive" src="Bilder/Logo.png">
    </div>
    <div class="col-md-8">
      <blockquote>
        <p class="h2">Navigation for indoor events</p>
      </blockquote>
      <button type="button" class="btn btn-default btn-lg btnNeueKarteHinzufuegen">
        <span class="glyphicon glyphicon glyphicon-plus" aria-hidden="true"> Create new Map</span>
      </button>
      <button type="button" class="btn btn-default btn-lg btnGetTheApp">
        <i class="material-icons btnGetTheApp">android</i> Get the App
      </button>
    </div>
  </div>
</div>

<div class="container content">
  <div class="row">
    <h2 class="page-header">About</h2>
    <div class="col-md-4">
      <img class="bild logo" src="Bilder/Logo.png">
    </div>
    <div class="col-md-8">
      <p>The <b>NavEvent App</b> is a App to find your way at events. You can read information about interesting places there or
        display where are the toilets. If you are a person who organizes an event the <b>NavEvent App</b> is perfect for you. You can easily and fast create a map.
        To do this all you just need an Android Smartphone, a PC, and IBeacons. You can buy the IBeacons
        for example here: <a href="http://store.easibeacon.com/Beacon-Packs_c_1.html" style="color: #66f" target="_blank">store.easibeacon.com</a> </p>
    </div>
  </div>
  <div class="row">
    <h2 class="page-header">Create new Map</h2>
    <div class="col-md-4">
      <img class="bild logo" src="Bilder/create_map.png">
    </div>
    <div class="col-md-8">
      <p>Click on the Button to Create a Map for your event. There will be a guide which will help you. The map you created, then will
         be accessible for all people who want to go to your event.</p>
         <button type="button" class="btn btn-default btn-lg btnNeueKarteHinzufuegen">
           <span class="glyphicon glyphicon glyphicon-plus" aria-hidden="true"> Create new Map</span>
         </button>
    </div>

  </div>
</div>
