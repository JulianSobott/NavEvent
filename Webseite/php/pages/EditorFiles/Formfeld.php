<div class="seite seite-rechts <?php if(isset($_GET['status']))if($_GET['status']=="edit")echo "animate-in"?>">
  <div class="mask_form">
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
        <h3>Properties</h3>
      </div>
    </div>
    <form id="daten" action="datenbank.inc.php" method="post">
      <div class="name">
        <label for="tfName">Name: </label>
        <input onchange="saveData(this.name, this.value)" id="tfName" class="eingabe tfName" type="text" name="name" placeholder="z.B. Labor" value="">
      </div>
      <div class="besonders feld">
        <div class="specialPlace">
          <label>Special place:</label>
          <fieldset>
            <label for="rdbBesondersJa">Yes</label>
            <input class="rdbBesondersJa" type="radio" name="rdbBesonders" value="Besonderer_Ort" <?php //if($data['besonders']==1) echo 'checked = "checked"'?>>
            <label for="rdbBesondersNein">No</label>
            <input class="rdbBesondersNein" checked="checked" onclick="saveData(this.name, this.value)" type="radio" name="rdbBesonders" value="kein_Besonderer_Ort" <?php //if($data['besonders']==2 || $data['besonders']==0) echo 'checked = "checked"'?>>
          </fieldset>
          <i class="material-icons dropDown">arrow_drop_down</i>
        </div>
        <div class="listSpecialPlaces">
          <fieldset class="dropDownList dlFieldset">
            <ul class="dropDownList">
              <li>
                <input type="radio" id="spToilets" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace toilet" value="Toiletten">
                <label for="spToilets">Toilets</label>
              </li>
              <li>
                <input type="radio" id="spCafeteria" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace cafeteria" value="Cafeteria">
                <label for="spCafeteria">Cafeteria</label>
              </li>
              <li>
                <input type="radio" id="spEmegencyExit" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace notausgang" value="Notausgang">
                <label for="spEmegencyExit">Exit</label>
              </li>
              <li>
                <input type="radio" id="spInfopoint" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace infopoint" value="Infopoint">
                <label for="spInfopoint">Infopoint</label>
              </li>
              <li>
                <input type="radio" id="spOther" onclick="saveData(this.name, this.value)" name="rdbSpecialPlace" class="specialPlace sonstiges" value="Other">
                <input type="text" name="tfSpecialPlace" class="specialPlace tfSonstiges" placeholder="Other">
              </li>
            </ul>

          </fieldset>
        </div>
      </div>
      <div class="form-group feld">
        <label for="tfInfos">Information: </label>
        <textarea id="tfDescription" class="form-control" onchange="saveData(this.name, this.value)" type="text" name="description" rows="5" value=""></textarea>
      </div>
      <input class="submit" type="button" name="submit" value="Save Data">
    </form>
  </div>

</div>
