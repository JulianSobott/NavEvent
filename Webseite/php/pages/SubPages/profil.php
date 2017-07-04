<?php
$account_id = $_SESSION['accountId'];
?><input type="checkbox" id="toggleSidebar" name="" value="">
<div class="body">
  <label class="toggleSidebar" for="toggleSidebar">☰</label>
  <div class="seitenmenue">
    <h2>Navigation</h2>
    <ul class="navigation">
      <li class="hide">My Maps</li>
      <li class="hide">edit profile</li>
      <li><a href="php/includes/logout.inc.php?action=logout">Logout</a></li>
    </ul>
    <h3 class="hide">Anhang</h3>
    <ul class="anhang hide">
      <li><div class="material-icons icons SM settings">settings</div>
      Settings</li>
      <li><div class="material-icons icons SM help">help</div>
      Help</li>
      <li><div class="material-icons icons SM description">description</div>
      Impressum</li>
      <li class="linkSeiteAnpassen"><div class="material-icons icons SM description">edit</div>
      Customize page</li>
    </ul>
  </div>

  <div class="content">
    <div id="dialog_delete_map" title="Delete the map?">
      <p><span class="ui-icon ui-icon-alert" style=" float:left; margin:12px 12px 20px 0;"></span>This map will be permanently deleted and cannot be recovered. Are you sure?</p>
    </div>
    <div class="meineKarten">
      <h2>My maps</h2>
      <div class="row kartenBilder">
        <div class="col-md-1 button btnMap btnNeueKarteHinzufuegen" onclick="mapEditor(<?php if(isset($_SESSION["nutzername"]))echo $_SESSION["accountId"]?>)">
          <div class="material-icons add">add</div>
          <p>Create new map</p>
        </div>
        <?php

        if(isset($account_id)){
          $sql = "SELECT * FROM maps WHERE fk_account_id = '$account_id'";
          $res = mysqli_query($con, $sql);
          while ($result = mysqli_fetch_assoc($res)) {
            $map_id = $result['id'];
            $img = $result['img_file'];
            $mime = explode('/', $result['mime']);
            $mime = $mime[0];
            $map_name = $result['name'];
            ?>
            <div class=" col-md-1 button btnMap map_<?php echo $map_id?>">
              <img id="bild" alt="<?php echo $map_id ?>" src="uploads/<?php echo $img.'.'.$mime?>"/>
              <div class="map_name">
                <?php echo $map_name?>
              </div>
              <div class="option_field">
                <div class="o_field delete">
                  <i class="material-icons miDelete btnDelete" onclick="dialog_delete_map(<?php echo $map_id; ?>)">delete</i>
                </div>
                <div class="o_field edit">
                  <i class="material-icons miEdit btnEdit" onclick="edit_map(<?php echo $map_id; ?>)">edit</i>
                </div>
              </div>
            </div>
            <?php
          }
        }else {
          include 'anmelden.php';
        } ?>

      </div>
      </div>
    </div>
</div>
