<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>NavEvent</title>
    <link rel="stylesheet" href="CSS/main.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
  </head>
  <body>

    <?php include("header.php") ?>

    <div class="startInfo">
      <img class="bild logo" src="Bilder/NavEventLogo.jpeg">
      <div class="bildershow">
        <img class="bild karte karte-1" src="Bilder/Bildershow/GebäudePlan.PNG" alt="Beispiel Karte">
        <img class="bild karte karte-2" src="Bilder/Bildershow/GebäudePlan02.jpg" alt="Beispiel Karte">
        <img class="bild karte karte-3" src="Bilder/Bildershow/GebäudePlan2.PNG" alt="Beispiel Karte">
        <img class="bild karte karte-4" src="Bilder/Bildershow/GebäudePlan2_1.PNG" alt="Beispiel Karte">
      </div>

      <div class="leitsatz">
        Leit Satz..
      </div>
    </div>

    <div class="clearfix ueberDieApp">
      <h2>Über die App</h2>
      <div class="picture aboutPicture">
        <img class="bild logo" src="Bilder/NavEventLogo.jpeg">
      </div>
      <p class="text">Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
        sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
        sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
        Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum
        dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
         dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores
          et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor
        sit amet.</p>
    </div>

    <div class="neueKarte">
      <h2>Neue Karte hinzufügen</h2>
      <div class="clearfix">
        <div class=" picture newCardPicture">
          <img class="bild logo" src="Bilder/NavEventLogo.jpeg">
        </div>
        <p class="text">Hier können Sie eine neue Karte für ihre Veranstaltung entwerfen und individuell an ihre Bedürfnisse anpassen.
           Klicken Sie hiefür auf den Knopf <b>Neue Karte hinzufügen</b> und volgen Sie dem Guide der Sie durch das erstellen führen wird.</p>
      </div>
      <div class="button btnNeueKarteHinzufuegen">
        <div class="material-icons add">add</div>
        <p>Neue Karte hinzufügen</p>
      </div>

    </div>
    <?php include 'footer.php'; ?>
  </body>

  <script src="JS/JavaScriptMain.js"> </script>
</html>
