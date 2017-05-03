<?php
error_reporting(E_ALL);
include 'DatenbankConnect.inc.php';
if (isset($_GET['beaconId'])) {
  $beaconId = $_GET['beaconId'];
  $sql = mysql_query("SELECT * FROM `beacons` WHERE `beaconId` = '".$beaconId."'");
  $data = mysql_fetch_array($sql);
  echo $data['name']." ".$data['beaconId'].'<br />';
}

/*$actualBeacon = 1;
if (isset($_POST['beaconId']))
{
  echo "Done";
  $actualBeacon = $_POST['beaconId'];
  echo $actualBeacon;
}
echo "Heloo1".'<br />';
$sql = mysql_query("SELECT * FROM `karte01` WHERE `beaconId` = $actualBeacon ");
$data = mysql_fetch_array($sql);
echo $data['name']." ".$data['beaconId'].'<br />';*/
?>

<div class="beaconBearbeiten" draggable="true" ondragstart="drag(event)">
  <i class="material-icons close">close</i>
  <form id="daten" action="datenbank.inc.php" method="post">
    <div class="name">
      <label for="tfName">Name: </label>
      <input id="tfName" class="eingabe tfName" type="text" name="tfName" placeholder="z.B. Labor" value=<?php
      if (isset($_GET['beaconId'])) {
         echo $data['name'];
      }else {
        echo "default";
      }?>>
    </div>
    <div class="besonders">
      <div class="specialPlace">
        <label>Besonderer Ort:</label>
        <fieldset>
          <label for="rdbBesondersJa">Ja</label>
          <input class="rdbBesondersJa" type="radio" name="rdbBesonders" value="Besonderer_Ort" <?php //if($data['besonders']==1) echo 'checked = "checked"'?>>
          <label for="rdbBesondersNein">Nein</label>
          <input class="rdbBesondersNein" type="radio" name="rdbBesonders" value="kein_Besonderer_Ort" <?php //if($data['besonders']==2 || $data['besonders']==0) echo 'checked = "checked"'?>>
        </fieldset>
        <i class="material-icons dropDown">arrow_drop_down</i>
      </div>
      <div class="listSpecialPlaces">
        <fieldset class="dropDownList dlFieldset">
          <ul class="dropDownList">
            <li>
              <input type="radio" name="rdbSpecialPlace" class="specialPlace toilet" value="Toiletten">
              <label for="rdbSpecialPlace">Toiletten</label>
            </li>
            <li>
              <input type="radio" name="rdbSpecialPlace" class="specialPlace cafeteria" value="Cafeteria">
              <label for="rdbSpecialPlace">Cafeteria</label>
            </li>
            <li>
              <input type="radio" name="rdbSpecialPlace" class="specialPlace notausgang" value="Notausgang">
              <label for="rdbSpecialPlace">Notausgang</label>
            </li>
            <li>
              <input type="radio" name="rdbSpecialPlace" class="specialPlace infopoint" value="Infopoint">
              <label for="rdbSpecialPlace">Infopoint</label>
            </li>
            <li>
              <input type="radio" name="rdbSpecialPlace" class="specialPlace sonstiges" value="sonstiges">
              <input type="text" name="tfSpecialPlace" class="specialPlace tfSonstiges" placeholder="Sonstiges">
            </li>
          </ul>

        </fieldset>
      </div>
    </div>
    <div class="infos">
      <label for="tfInfos">Informationen: </label>
      <textarea class="eingabe tfInfos" type="text" name="tfInfos" value="Infosd">
      </textarea>
      <div class="expand"> Mehr...</div>
    </div>
    <input class="submit" type="submit" name="submit" value="Daten Speichern">
    <div class="material-icons deleteBeacon">delete</div>
  </form>
</div>
