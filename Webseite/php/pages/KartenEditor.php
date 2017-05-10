<?php
session_start();
require '../includes/DatenbankConnect.inc.php';

/*
include 'DatenbankConnect.inc.php';
if (isset($_GET['beaconId'])) {
  $beaconId = $_GET['beaconId'];
  $sql = mysql_query("SELECT * FROM `beacons` WHERE `beaconId` = '".$beaconId."'");
  $data = mysql_fetch_array($sql);
  echo $data['name']." ".$data['beaconId'].'<br />';
}

$actualBeacon = 1;
if (isset($_POST['beaconId']))
{
  echo "Done";
  $actualBeacon = $_POST['beaconId'];
  echo $actualBeacon;
}
echo "Heloo1".'<br />';
$sql = mysql_query("SELECT * FROM `karte01` WHERE `beaconId` = $actualBeacon ");
$data = mysql_fetch_array($sql);
echo $data['name']." ".$data['beaconId'].'<br />';
*/
?>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="../../CSS/bootstrap.min.css">
    <link rel="stylesheet" href="../../CSS/karteneditor.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <!--<script src="//cloud.tinymce.com/stable/tinymce.min.js"></script>
    <script>tinymce.init({ selector:'textarea' });</script>-->
    <meta charset="utf-8">
    <title>Karte Bearbeiten</title>
  </head>
  <body>
    <span class="glyphicon glyphicon-upload" aria-hidden="true"></span>
    <?php include 'EditorFiles/EditorHeader.php'; ?>
    <div class="content">
      <?php
      include 'EditorFiles/Seitenmenue.php';
      include 'EditorFiles/KartenContainer.php';
      include 'EditorFiles/Formfeld.php';
      ?>
      <div class="publishMap">
        <button type="button" name="publishMap"><i class="material-icons miPublish">publish</i></button>
        <div class="textPublishMap">
          &nbspKarte hochladen und veröffentlichen&nbsp
        </div>
      </div>
    </div>
  </body>
  <script src="../../JS/JsKarteBearbeiten.js"></script>
</html>
