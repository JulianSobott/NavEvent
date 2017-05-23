<?php
function showData($field)
{
  $minor_id = $_POST['id'];
  $fk_map_id = $_POST['fk_map_id'];
  $sql = "SELECT * FROM beacons WHERE minor_id = '$minor_id' AND fk_map_id = '$fk_map_id'";
  $res = mysqli_query($con, $sql);
  while($result = mysqli_fetch_assoc($res)){
    $name = $result['name'];
    $fk_special = $result['fk_special'];
    $description = $result['description'];
  }
  if($field == "name")
    return $name;
  elseif ($field == "description")
    return $description;
}
if (isset($_POST['action'])) {
  var_dump($_POST);
}
 ?>

<div class="seite seite-rechts <?php if(isset($_GET['status']))if($_GET['status']=="edit")echo "animate-in"?>">
  <div class="mask">
    Click on the Map to add a Beacon
  </div>
  <!--
  <form class="beaconBearbeiten" action="" method="post">
    <div class="form-group">
      <label for="beaconName">Name: </label>
      <input type="text" name="form-control" value="" placeholder="z.B. Labor">
    </div>
    <div class="form-group">
      <div class="form-check">
        <label class="form-check-label">
          <input type="radio" name="optionsRadio" id="besondersJa" value="Ja">
        </label>
      </div>
      <div class="form-check">
        <label class="form-check-label">
          <input type="radio" name="optionsRadio" id="besondersNein" value="Nein">
        </label>
      </div>
    </div>

  </form>-->
  <div class="beaconBearbeiten">
    <div class="progress">
      <div class="progress-bar progress-bar-striped <?php if(isset($_POST['action'])) echo 'progress-bar-danger';?>" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
        <span class="sr-only">45% Complete</span>
      </div>
    </div>
    <div class="feld header">
      <div class="delete">
        <i class="material-icons miDelete btnDelete">delete</i>
      </div>
      <div class="h3left">
        <h3>Eigenschaften</h3>
      </div>
    </div>
    <form id="daten" action="datenbank.inc.php" method="post">
      <div class="name">
        <label for="tfName">Name: </label>
        <input onchange="saveData(name, this.value) "id="tfName" class="eingabe tfName" type="text" name="name" placeholder="z.B. Labor" value="<?php
        if(isset($_POST['action'])){
          echo "Günther3";
          showData("name");
          echo "Günther2";
        }
        echo "Günther";?>">
      </div>
      <div class="besonders feld">
        <div class="specialPlace">
          <label>Besonderer Ort:</label>
          <fieldset>
            <label for="rdbBesondersJa">Ja</label>
            <input class="rdbBesondersJa" type="radio" name="rdbBesonders" value="Besonderer_Ort" <?php //if($data['besonders']==1) echo 'checked = "checked"'?>>
            <label for="rdbBesondersNein">Nein</label>
            <input class="rdbBesondersNein" checked="checked" onclick="saveData(this.name, this.value)" type="radio" name="rdbBesonders" value="kein_Besonderer_Ort" <?php //if($data['besonders']==2 || $data['besonders']==0) echo 'checked = "checked"'?>>
          </fieldset>
          <i class="material-icons dropDown">arrow_drop_down</i>
        </div>
        <div class="listSpecialPlaces">
          <fieldset class="dropDownList dlFieldset">
            <ul class="dropDownList">
              <li>
                <input type="radio" id="spToilets" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace toilet" value="Toiletten">
                <label for="spToilets">Toiletten</label>
              </li>
              <li>
                <input type="radio" id="spCafeteria" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace cafeteria" value="Cafeteria">
                <label for="spCafeteria">Cafeteria</label>
              </li>
              <li>
                <input type="radio" id="spEmegencyExit" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace notausgang" value="Notausgang">
                <label for="spEmegencyExit">Notausgang</label>
              </li>
              <li>
                <input type="radio" id="spInfopoint" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace infopoint" value="Infopoint">
                <label for="spInfopoint">Infopoint</label>
              </li>
              <li>
                <input type="radio" id="spOther" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace sonstiges" value="sonstiges">
                <input type="text" name="tfSpecialPlace" class="specialPlace tfSonstiges" placeholder="Sonstiges">
              </li>
            </ul>

          </fieldset>
        </div>
      </div>
      <div class="form-group feld">
        <label for="tfInfos">Informationen: </label>
        <textarea class="form-control" onchange="saveData(this.name, this.value)" type="text" name="description" rows="5" value="<?php
        if($_POST['action'] == 'show'){
          showData("name");}
        ?>"></textarea>
      </div>
      <input class="submit" type="button" name="submit" value="Daten Speichern">
    </form>
  </div>

</div>
