<?php
session_start();
error_reporting(E_ALL);
//Verbindung mit Datenbank herstellen
require 'DatenbankConnect.inc.php';

//------Daten in Datenbank rein schreiben--------
if(isset($_POST['name']))
{
  $kartenId = "5";
  $beaconId = $_POST['beaconId'];
  $beaconId = str_replace("beacon-", "", $beaconId);
  $name = $_POST['name'];
  $besonders = $_POST['besonders'];
  $besondersName = $_POST['besondersName'];
  $informationen = $_POST['informationen'];
  $posX = $_POST['posX'];
  $posY = $_POST['posY'];

  //TODO alle Felder hinzufuegen

  $sql = "INSERT INTO `beacons`
  (
    `id`,
    `fk_kartenId`,
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
    '$kartenId',
    '$beaconId',
    '$name',
    '$besonders',
    '$besondersName',
    '$informationen',
    '$posX',
    '$posY'
  );";

  $insert = mysqli_query($con, $sql);

}

//----------Karten Name in Db schreiben-----------
if (isset($_POST['kartenName'])) {
  $kartenName = $_POST['kartenName'];
  $sql = mysql_query("SELECT `kartenId` FROM `karten` WHERE `kartenName` = '".$kartenName."' ");
  $kartenId = mysql_fetch_array($sql);
  $_SESSION['kartenId'] = $kartenId['kartenId'];
  echo $_SESSION['kartenId'];
  $sql = "INSERT INTO `karten`
  (
    `kartenId`,
    `kartenName`,
    `fk_accountId`
  )
  VALUES
  (
    NULL,
    '$kartenName',
    '1'
  );";

  $insert = mysql_query($sql);
}
?>
