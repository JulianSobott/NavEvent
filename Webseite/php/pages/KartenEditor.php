<?php
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
    <?php include 'EditorFiles/EditorHeader.php'; ?>
    <div class="content">
      <?php
      include 'EditorFiles/Seitenmenue.php';
      include 'EditorFiles/KartenContainer.php';
      include 'EditorFiles/Formfeld.php';
      ?>
    </div>
  </body>
  <script src="../../JS/JsKarteBearbeiten.js"></script>
</html>
