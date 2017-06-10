<?php
//session_start();
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

if ($_POST['action'] === "map_name") {
  $map_name = $_POST['map_name'];
  $map_id = $_POST['map_id'];
  $sql = "UPDATE maps SET name = '$map_name' WHERE id = '$map_id'";
  $res = mysqli_query($con, $sql);
}

if($_POST['action'] === "updateSidebar" && isset($_POST['map_id'])){
  if (isset($_POST['action'])){
    if ($_POST['action'] == "updateSidebar") {
      $map_id = $_POST['map_id'];
      $sql = "SELECT * FROM beacons WHERE fk_map_id = '$map_id'";
      $res = mysqli_query($con, $sql);
      while($result = mysqli_fetch_assoc($res)){
        $name = $result['name'];
        $description = $result['description'];
        ?>
        <div class="beaconInfoContainer">
          <div class="beaconInfo biName">
            <?php echo $name; ?>
          </div>
          <div class="beaconInfo biDescription">
            <?php echo $description; ?>
          </div>
          <div class="beaconInfo biSpecial">

          </div>
        </div>
        <?php
      }
    }
  }
}

if ($_POST['action'] === "updateBeaconPosition") {
  $minor_id = $_POST['minor_id'];
  $fk_map_id = $_POST['fk_map_id'];
  $pos_x = $_POST['pos_x'];
  $pos_y = $_POST['pos_y'];
  $sql = "UPDATE beacons SET pos_x = '$pos_x', pos_y = '$pos_y' WHERE minor_id = '$minor_id' AND fk_map_id = '$fk_map_id'";
  $res = mysqli_query($con, $sql);
  if ($res) {
    echo $res;
  }

}

if ($_POST['action'] === "show") {
  $minor_id = $_POST['minor_id'];
  $fk_map_id = $_POST['fk_map_id'];
  $sql = "SELECT * FROM beacons WHERE minor_id = '$minor_id' AND fk_map_id = '$fk_map_id'";
  $res = mysqli_query($con, $sql);
  while($result = mysqli_fetch_assoc($res)){
    $name = $result['name'];
    $fk_special = $result['fk_special'];
    $description = $result['description'];
    echo json_encode($result);
  }
}

if ($_POST['action'] === "delete_map") {
  $map_id = $_POST['map_id'];
  $sql = "DELETE FROM maps WHERE id='$map_id'";
  $result = mysqli_query($con, $sql);
  echo "ready";
}
?>
