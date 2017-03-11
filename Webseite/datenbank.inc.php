<?php
error_reporting(E_ALL);
//Verbindung mit Datenbank herstellen
include 'DatenbankConnect.inc.php';

//------Daten in Datenbank rein schreiben--------
if(isset($_POST['name']))
{
  $beaconId = $_POST['beaconId'];
  $beaconId = str_replace("beacon-", "", $beaconId);
  $name = $_POST['name'];
  $besonders = $_POST['besonders'];
  $besondersName = $_POST['besondersName'];
  $informationen = $_POST['informationen'];
  $posX = $_POST['posX'];
  $posY = $_POST['posY'];

  //TODO alle Felder hinzufuegen

  $sql = "INSERT INTO `karte01`
  (
    `id`,
    `beaconId`,
    `name`,
    `besonders`,
    `besondersName`,
    `informationen`,
    `posX`,
    `posY`
  )
  VALUES
  (
    Null,
    '$beaconId',
    '$name',
    '$besonders',
    '$besondersName',
    '$informationen',
    '$posX',
    '$posY'
  );";

  $insert = mysql_query($sql);

}

?>
