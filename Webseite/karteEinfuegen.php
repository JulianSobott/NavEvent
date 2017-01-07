<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Karte Einfügen</title>
    <link rel="stylesheet" href="CSS/karteEinfuegen.css">
    <link rel="stylesheet" href="CSS/FlowMenu.css">
    <link rel="stylesheet" href="CSS/main.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
  </head>
  <body>
    <div class="header">
      <img src="Bilder/NavEventLogo.jpeg">
      <a href="index.html"><h1>NavEvent</h1></a>
    </div>

    <div class="content">
      <div class="flow_menu">
        <div class="material-icons pfeil rechtspfeil"> chevron_right</div>
        <div class="material-icons pfeil linkspfeil"> chevron_left</div>
        <div id="slides">
          <div class="slide slide1">
            <div class="hilfe hilfe01">

            </div>
            <form name="uploadformular" enctype="multipart/form-data" action="karteEinfuegen.php" method="post" >
              <input type="file" name="uploaddatei" size="60" maxlength="255" >
              <input type="Submit" name="submit" value="Datei hochladen" class="btnUpload">
            </form>

            <div class="spinner spinner1">            </div>

            <?php
            // löscht alle Bilder aus den Verzeichnis
            array_map('unlink', glob("uploads/*"));

            if ( !empty($_FILES))
            {
                // Datei wurde durch HTML-Formular hochgeladen
                // und kann nun weiterverarbeitet werden
                $erlaubteEndungen = array('png', 'jpg', 'jpeg', 'gif');
                $endung = strtolower(pathinfo($_FILES['uploaddatei']['name'],PATHINFO_EXTENSION));
                $bildinfo = pathinfo($_FILES['uploaddatei']['name']);
                if(in_Array($endung, $erlaubteEndungen)){
                  move_uploaded_file (
                       $_FILES['uploaddatei']['tmp_name'] ,
                       'uploads/'. $_FILES['uploaddatei']['name'] );

                  echo "<p id='pHochgeladen'>Datei erfolgreich hochgeladen</p>";

                }else {
                  echo "<p class='pError'>ERROR: Keine passende Datei ausgewählt</p>";
                }
            }

            //TODO Nur Bild anzeigen welches hochgeladen wurde!!

            $ordner = 'uploads';

            $alleBilder = scandir($ordner);

            foreach ($alleBilder as $bild) {
              $bildinfo = pathinfo($ordner."/".$bild);
              $size = ceil(filesize($ordner."/".$bild)/1024);

              if ($bild != "." && $bild != ".."){
                ?>
                <img src="<?php echo $bildinfo['dirname']."/".$bildinfo['basename'];?>" width="800" alt="Vorschau" />
                <?php
              }
            }
            ?>

          </div>
          <div class="slide slide2">      </div>
          <div class="slide slide3">      </div>
        </div>

        <div id="counter">
        </div>
      </div>
    </div>



    <div class="footer">
      <span>About</span>
      <span>Impressum</span>
      <span>Kontakt</span>
    </div>
  </body>
  <script src="JS/JsKarteEinfuegen.js"></script>
  <script src="JS/JsFlowMenu.js"></script>
</html>
