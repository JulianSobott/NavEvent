<?php
session_start();
error_reporting(E_ALL);
require 'DatenbankConnect.inc.php';

//------Daten in Datenbank rein schreiben-------
if($_POST['action'] === "insert"){
  if(isset($_POST['name']))
  {
    $name = $_POST['name'];
    $minor_id = $_POST['minor_id'];
    $minor_id = str_replace("beacon-", "", $minor_id);
    $pos_x = $_POST['pos_x'];
    $pos_y = $_POST['pos_y'];
    $description = $_POST['description'];
    $fk_map_id = $_POST['fk_map_id'];
    $fk_special = $_POST['fk_special'];
    $fk_ordinary = $_POST['fk_ordinary'];

    $sql = "INSERT INTO `beacons`
    (
      `id`,
      `name`,
      `minor_id`,
      `pos_x`,
      `pos_y`,
      `description`,
      `fk_map_id`,
      `fk_special`,
      `fk_ordinary`
    )
    VALUES
    (
      Null,
      '$name',
      '$minor_id',
      '$pos_x',
      '$pos_y',
      '$description',
      '$fk_map_id',
      '$fk_special',
      '$fk_ordinary'
    );";

    $insert = mysqli_query($con, $sql);
  }
}

if ($_POST['action'] === "update") {
  if(isset($_POST['value'])){
    $map_id = $_POST['map_id'];
    $minor_id = $_POST['minor_id'];
    $field = $_POST['field'];
    $value = $_POST['value'];
    //If position is needed
    /*
    $position = $_POST['position'];
    $position = str_replace("left: ", "", $position);
    $position = str_replace("top: ", "/", $position);
    $position = str_replace("%;", "", $position);
    $position = explode("/", $position);
    $position_left = trim($position[0]);
    $position_top = trim($position[1]);
    */
    $sql = "UPDATE beacons SET ".$field." = '$value' WHERE minor_id = '$minor_id' AND fk_map_id = '$map_id'";
    $result = mysqli_query($con, $sql);

  }
}

if($_POST['action'] === "delete"){
  $map_id = $_POST['map_id'];
  $minor_id = $_POST['minor_id'];
  $sql = "DELETE FROM beacons WHERE minor_id='$minor_id' AND fk_map_id='$map_id'";
  $result = mysqli_query($con, $sql);
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
    `id`,
    `name`,
  )
  VALUES
  (
    NULL,
    '$kartenName',
  );";

  $insert = mysqli_query($sql);
}
?>
